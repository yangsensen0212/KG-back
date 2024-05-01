package com.yss.cad.service.impl;

import com.yss.cad.domain.DrillColumnCell;
import com.yss.cad.exception.NotDirException;
import com.yss.cad.mapper.DXFMapper;
import com.yss.cad.service.IDrillColumnService;
import com.yss.cad.utils.FileUtils;
import com.yss.dxf.collector.DxfCollector;
import com.yss.dxf.continuity.DxfLine;
import com.yss.dxf.continuity.DxfPoint;
import com.yss.dxf.continuity.IDxfText;
import com.yss.dxf.entity.GeometricLine;
import com.yss.dxf.entity.GeometricPoint;
import com.yss.dxf.entity.GeometricPolyLine;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;

/**
 * @Author 杨森森
 * @Data 2024/4/13  23:01
 */
@Service
public class DrillColumnServiceImpl implements IDrillColumnService {
    @Autowired
    private DXFMapper dxfMapper;

    /**
     * 提取信息成excel
     * 不支持递归文件夹
     * @param cadDir cad文件目录
     * @return 转换后的excel文件目录
     */
    @Override
    public File toExcelBatch(File cadDir) throws IOException {
        if(!cadDir.isDirectory()) {
            throw new NotDirException();
        }
        for (File file : Objects.requireNonNull(cadDir.listFiles())) {
            assert file.isFile() && FileUtils.getSuffix(file).equals(".dxf");
            File excel = toExcel(file);
        }
        return null;
    }

    /**
     * 提取信息成excel
     *
     * @param dxfFile cad文件
     * @return 转换后的excel文件目录
     */
    @Override
    public File toExcel(File dxfFile) throws IOException {
        getCellList(dxfFile);
        return null;
    }

    /**
     * 解析cad钻孔柱状图，得到单元格数据
     *
     * @param dxfFile dxf文件
     * @return 解析后的单元格数据
     */
    @Override
    public List<DrillColumnCell> getCellList(File dxfFile) throws IOException {
        DxfCollector collector = dxfMapper.getCollector(dxfFile);
        List<DxfLine> dxfLineList = collector.getDxfLineList();
        List<IDxfText> dxfTextList = collector.getDxfTextList();
        List<DxfLine> xLineList = new ArrayList<>();
        List<DxfLine> yLineList = new ArrayList<>();
        splitDxfLineList(dxfLineList, xLineList, yLineList);
        List<DrillColumnCell> drillColumnCellList =  new ArrayList<>();
        while (dxfTextList.size() > 0) {
            DrillColumnCell cell = getCell(dxfTextList, xLineList, yLineList);
            if (cell!=null) {
                drillColumnCellList.add(cell);
            }
        }
        return drillColumnCellList;
    }

    private DrillColumnCell getCell(List<IDxfText> dxfTextList, List<DxfLine> xLineList, List<DxfLine> yLineList) {
        IDxfText text = dxfTextList.get(0);
        double dMin1 = Double.MAX_VALUE;
        double dMin2 = Double.MAX_VALUE;
        DxfLine rLine = null;
        DxfLine lLine = null;
        DxfLine tLine = null;
        DxfLine dLine = null;
        for (DxfLine line : yLineList) {
            if (!textInLineRange(text, line, 1)) {
                continue;
            }
            double distance = textToLineDistance(text, line);
            List<DxfPoint> pointList = line.getDataPointList();
            int temp1 = pointList.get(0).getX().compareTo(text.getX());
            boolean temp2 = distance < dMin1;
            if (pointList.get(0).getX().compareTo(text.getX()) > 0 && distance < dMin1) {
                dMin1 = distance;
                rLine = line;
            }else if(pointList.get(0).getX().compareTo(text.getX()) < 0 && distance < dMin2) {
                dMin2 = distance;
                lLine = line;
            }
        }

        dMin1 = Double.MAX_VALUE;
        dMin2 = Double.MAX_VALUE;
        for (DxfLine line : xLineList) {
            if (!textInLineRange(text, line, 0)) {
                continue;
            }
            double distance = textToLineDistance(text, line);
            List<DxfPoint> pointList = line.getDataPointList();
            if (pointList.get(0).getY().compareTo(text.getY()) > 0 && distance < dMin1) {
                dMin1 = distance;
                tLine = line;
            }else if(pointList.get(0).getY().compareTo(text.getY()) < 0 && distance < dMin2) {
                dMin2 = distance;
                dLine = line;
            }
        }

        if (rLine==null || lLine == null || tLine==null || dLine == null) {
            return null;
        }
        // 继续寻找该范围内的其它text
        StringBuilder stringBuilder = new StringBuilder();
        List<IDxfText> valueList = new ArrayList<>();
        valueList.add(text);
        List<Integer> popList = new ArrayList<>();
        BigDecimal xMin = lLine.getDataPointList().get(0).getX();
        BigDecimal xMax = rLine.getDataPointList().get(0).getX();
        BigDecimal yMax = tLine.getDataPointList().get(0).getY();
        BigDecimal yMin = dLine.getDataPointList().get(0).getY();
        for (int i = 1; i < dxfTextList.size(); i++) {
            IDxfText text1 = dxfTextList.get(i);
            BigDecimal x = text1.getX();
            BigDecimal y = text1.getY();
            if (x.compareTo(xMin) > 0 && x.compareTo(xMax) < 0 && y.compareTo(yMax) < 0 && y.compareTo(yMin) > 0) {
                valueList.add(text1);
                popList.add(i);
            }
        }
        valueList.sort(Comparator.comparing(IDxfText::getY).thenComparing(IDxfText::getX).reversed());
        valueList.forEach(v -> stringBuilder.append(v.getTextContent()));
        // 倒序遍历索引列表, 防治弹出后影响原序列顺序
        Collections.reverse(popList);
        popList.forEach(i -> dxfTextList.remove(i.intValue()));
        dxfTextList.remove(0);
        return new DrillColumnCell()
                .setTextContent(stringBuilder.toString())
                .setTopLeft(new GeometricPoint().setX(xMin).setY(yMax))
                .setBottomRight(new GeometricPoint().setX(xMax).setY(yMin));
    }

    /**
     * 只保留垂直于y轴和x轴的直线，非直线删除
     * @param dxfLineList 原线段列表
     * @param xLineList 平行于x轴的列表
     * @param yLineList 平行于y轴的列表
     */
    private void splitDxfLineList(List<DxfLine> dxfLineList, List<DxfLine> xLineList, List<DxfLine> yLineList) {
        for (DxfLine line : dxfLineList) {
            if (!isStraight(line)) {
                continue;
            }
            GeometricLine geometricLine = transform(line);
            if (Math.abs(cosBetweenLineSegment(geometricLine, createGeometricLine(0., 0., 0., 0., 1., 0.))) <= 0.01) {
                // 平行于x轴
                xLineList.add(line);
            }else if(Math.abs(cosBetweenLineSegment(geometricLine, createGeometricLine(0., 0., 0., 1., 0., 0.))) <= 0.01) {
//              // 平行于y轴
                yLineList.add(line);
            }
        }
    }

    /**
     * 判断是否是直线
     * @return 是否
     */
    private boolean isStraight(DxfLine line) {
        List<DxfPoint> pointList = line.getDataPointList();
        for (int i = 2; i < pointList.size(); i++) {
            DxfPoint point1 = pointList.get(i);
            DxfPoint point2 = pointList.get(i);
            DxfPoint point3 = pointList.get(i);
            double x1 = point1.getX().doubleValue();
            double y1 = point1.getY().doubleValue();
            double x2 = point2.getX().doubleValue();
            double y2 = point2.getY().doubleValue();
            double x3 = point3.getX().doubleValue();
            double y3 = point3.getY().doubleValue();
            if (Math.abs(
                    ((x2 - x1) * (x3 - x1) + (y2 - y1) * (y3 - y1)) /
                            (Math.sqrt((x2 - x1) * (x2 - x1) + (y2 - y1) * (y2 - y1))
                                    *
                                    Math.sqrt((x3 - x1) * (x3 - x1) + (y3 - y1) * (y3 - y1)))
            )<=0.01) {
                return false;
            }
        }
        return true;
    }

    /**
     * 必须是直线
     * @param dxfLine dxfLine
     * @return 几何直线，只包含两个点
     */
    GeometricLine transform(DxfLine dxfLine) {
        GeometricLine geometricLine = new GeometricLine();
        List<DxfPoint> pointList = dxfLine.getDataPointList();
        DxfPoint start = pointList.get(0);
        DxfPoint end = pointList.get(pointList.size() - 1);
        geometricLine.setStartX(start.getX());
        geometricLine.setStartY(start.getY());
        geometricLine.setEndX(end.getX());
        geometricLine.setEndY(end.getY());
        return geometricLine;
    }

    /**
     * 计算某个点是否在直线所包含的x/y区间
     * @param text dxf文本
     * @param line dxf线段
     * @param axis 坐标类型 0为x方向，1为y方向
     * @return 是/否
     */
    boolean textInLineRange(IDxfText text, DxfLine line, int axis) {
        BigDecimal textCoord  = axis == 0? text.getX(): text.getY();
        List<DxfPoint> pointList = line.getDataPointList();
        pointList.sort((o1, o2) -> axis == 0 ? o1.getX().compareTo(o2.getX()) : o1.getY().compareTo(o2.getY()));
        int size = pointList.size();
        BigDecimal start = axis == 0 ? pointList.get(0).getX() : pointList.get(0).getY();
        BigDecimal end = axis == 0 ? pointList.get(size - 1).getX() : pointList.get(size - 1).getY();
        return textCoord.compareTo(start) > 0 && textCoord.compareTo(end) < 0;
    }

    /**
     * 文本到直线距离
     */
    double textToLineDistance(IDxfText text, DxfLine line) {
        double tx = text.getX().doubleValue();
        double ty = text.getY().doubleValue();
        List<DxfPoint> pointList = line.getDataPointList();
        DxfPoint point1 = pointList.get(0);
        DxfPoint point2 = pointList.get(pointList.size() - 1);
        double lx1 = point1.getX().doubleValue();
        double ly1 = point1.getY().doubleValue();
        double lx2 = point2.getX().doubleValue();
        double ly2 = point2.getY().doubleValue();
        if (lx1 == lx2) {
            //斜率无限大
            return Math.abs(lx1 - tx);
        }
        double k = (ly1 - ly2) / (lx1 - lx2);
        double b = ly1 - k * lx1;
        return Math.abs(k * tx - ty + b) / Math.sqrt(k * k + 1);
    }

    double cosBetweenLineSegment(GeometricLine line1, GeometricLine line2) {
        double x1 = line1.getStartX().doubleValue() - line1.getEndX().doubleValue();
        double y1 = line1.getStartY().doubleValue() - line1.getEndY().doubleValue();
        double x2 = line2.getStartX().doubleValue() - line2.getEndX().doubleValue();
        double y2 = line2.getStartY().doubleValue() - line2.getEndY().doubleValue();
        return (x1 * x2 + y1 * y2) / (Math.sqrt(x1 * x1 + y1 * y1) * Math.sqrt(x2 * x2 + y2 * y2));
    }

    GeometricLine createGeometricLine(double startX, double startY, double startZ, double endX, double endY, double endZ) {
        return new GeometricLine(
                new BigDecimal(startX), new BigDecimal(startY), new BigDecimal(startZ), new BigDecimal(endX), new BigDecimal(endY), new BigDecimal(endZ)
        );
    }
}
