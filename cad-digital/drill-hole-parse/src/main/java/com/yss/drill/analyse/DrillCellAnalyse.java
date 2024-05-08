package com.yss.drill.analyse;

import com.yss.drill.entity.DrillColumnCell;
import com.yss.drill.entity.IParamConfig;
import com.yss.drill.utils.MathOperate;
import com.yss.drill.utils.SplitDxfLine;
import com.yss.dxf.collector.DxfCollector;
import com.yss.dxf.continuity.DxfLine;
import com.yss.dxf.continuity.DxfPoint;
import com.yss.dxf.continuity.IDxfText;
import com.yss.dxf.entity.GeometricPoint;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @Author 杨森森
 * @Data 2024/4/19  14:48
 */
public class DrillCellAnalyse {
    private final List<DrillColumnCell> drillColumnCellList;
    private final DxfCollector collector;
    private final IParamConfig config;

    public DrillCellAnalyse(DxfCollector collector, IParamConfig config) {
        this.collector = collector;
        this.config = config;
        drillColumnCellList = analyse();

    }

    public List<DrillColumnCell> getDrillColumnCellList() {
        return drillColumnCellList;
    }

    private List<DrillColumnCell> analyse() {
        List<DxfLine> dxfLineList = collector.getDxfLineList();
        List<IDxfText> dxfTextList = collector.getDxfTextList();
        List<DxfLine> xLineList = new ArrayList<>();
        List<DxfLine> yLineList = new ArrayList<>();
        SplitDxfLine.split(dxfLineList, xLineList, yLineList, config.getCosBetweenLineSegmentErr(), config.getStraightErr());
        List<DrillColumnCell> drillColumnCellList =  new ArrayList<>();
        while (dxfTextList.size() > 0) {
            DrillColumnCell cell = getCell(dxfTextList, xLineList, yLineList);
            if (cell!=null) {
                drillColumnCellList.add(cell);
            }
        }
        return drillColumnCellList;
    }

    private DxfLine[] searchBorder(List<DxfLine> lineList, Integer axis, IDxfText text) {
        DxfLine line1 = null;
        DxfLine line2 = null;
        double dMin1 = Double.MAX_VALUE;
        double dMin2 = Double.MAX_VALUE;
        for (DxfLine line : lineList) {
            if (!MathOperate.textInLineRange(text, line, axis)) {
                continue;
            }
            double distance = MathOperate.textToLineDistance(text, line);
            List<DxfPoint> pointList = line.getDataPointList();
            BigDecimal p = axis==1? pointList.get(0).getX(): pointList.get(0).getY();
            BigDecimal t = axis==1? text.getX(): text.getY();
            if (p.compareTo(t) > 0 && distance < dMin1) {
                dMin1 = distance;
                line1 = line; // 右边或上边
            }else if(p.compareTo(t) < 0 && distance < dMin2) {
                dMin2 = distance;
                line2 = line; // 左边或下边
            }
        }
        return new DxfLine[] {line1, line2};
    }

    private DrillColumnCell searchText(IDxfText text, DxfLine lLine, DxfLine rLine, DxfLine tLine, DxfLine dLine, List<IDxfText> dxfTextList) {
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
            if (x.compareTo(xMin) >= 0 && x.compareTo(xMax) <= 0 && y.compareTo(yMax) <= 0 && y.compareTo(yMin) >= 0) {
                valueList.add(text1);
                popList.add(i);
            }
        }
        MathOperate.textSorted(valueList, config.getTextSortedErrRatio());
        valueList.forEach(v -> stringBuilder.append(v.getTextContent().replaceAll("\\s+", "")));
        // 倒序遍历索引列表, 防治弹出后影响原序列顺序
        Collections.reverse(popList);
        popList.forEach(i -> dxfTextList.remove(i.intValue()));
        dxfTextList.remove(0);
        return new DrillColumnCell()
                .setTextContent(stringBuilder.toString())
                .setTopLeft(new GeometricPoint().setX(xMin).setY(yMax))
                .setBottomRight(new GeometricPoint().setX(xMax).setY(yMin));
    }


    private DrillColumnCell getCell(List<IDxfText> dxfTextList, List<DxfLine> xLineList, List<DxfLine> yLineList) {
        IDxfText text = dxfTextList.get(0);
        Object[] lines = searchBorder(yLineList, 1, text);
        DxfLine rLine = (DxfLine) lines[0];
        DxfLine lLine = (DxfLine) lines[1];
        lines = searchBorder(xLineList, 0, text);
        DxfLine tLine = (DxfLine) lines[0];
        DxfLine dLine = (DxfLine) lines[1];

        if (rLine==null || lLine == null || tLine==null || dLine == null) {
            dxfTextList.remove(0);
            return null;
        }
        return searchText(text, lLine, rLine, tLine, dLine, dxfTextList);
    }
}
