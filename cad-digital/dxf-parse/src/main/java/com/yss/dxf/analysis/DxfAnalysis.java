package com.yss.dxf.analysis;

import com.google.common.collect.Lists;
import org.apache.commons.collections4.CollectionUtils;
import com.yss.dxf.entity.*;
import com.yss.dxf.enums.*;
import com.yss.dxf.exception.DxfAnalysisException;
import com.yss.dxf.util.DecimalCheckUtil;

import java.io.BufferedReader;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


public class DxfAnalysis {


    /**
     * 返回几何图像解析数据
     *
     * @param reader BufferedReader
     * @return 解析的数据
     * @throws IOException IO异常
     */
    public static Map<String, List<GeometricObject>> getGeometricListMap(BufferedReader reader) throws IOException {
        Map<String, List<GeometricObject>> map = new HashMap<>();
        //读取dxf所有的数据
        List<String> lineList = readAllLine(reader);
        parseFile(lineList, map);
        return map;
    }

    /**
     * 解析dxf文件结构
     *
     * @param lineList 总数据
     * @param map      接收解析的数据map
     */
    private static void parseFile(List<String> lineList, Map<String, List<GeometricObject>> map) {
        if (CollectionUtils.isEmpty(lineList)) {
            return;
        }
        int i = 0;
        String str = lineList.get(i);
        //未到文件结束标志
        while (!FileStructEnum.FILE_END.getCode().equals(str) && ++i < lineList.size()) {
            str = lineList.get(i);
            //实体段开始
            if (FileStructEnum.ENTITIES_START.getCode().equals(str)) {
                //解析实体
                parseEntities(i, lineList, map);
            }
            // 文件循环语句结束
        }
        // 解析函数结束
    }

    /**
     * 解析实体
     *
     * @param i        实体开始读取的行数
     * @param lineList 总数据
     * @param map      接收解析的数据map
     */
    private static void parseEntities(int i, List<String> lineList, Map<String, List<GeometricObject>> map) {
        String str = null;
        while (++i < lineList.size()) {
            str = lineList.get(i);

            //点开始
            if (PointEnum.POINT_NAME.getCode().equals(str)) {
                i = getPoint(i, lineList, map);
            }

            //圆开始
            if (CircleEnum.CIRCLE_NAME.getCode().equals(str)) {
                i = getCircle(i, lineList, map);
            }

            // 椭圆开始
            if (EllipseEnum.ELLIPSE_NAME.getCode().equals(str)) {
                // TODO 解析椭圆
            }

            //ARC实体开始
            if (ArcEnum.ARC_NAME.getCode().equals(str)) {
                i = getArc(i, lineList, map);
            }

            //直线开始
            if (LineEnum.LINE_NAME.getCode().equals(str)) {
                i = getLine(i, lineList, map);
            }

            // 多线段
            if (PolyLineEnum.POLYLINE_NAME.getCode().equals(str)) {
                i = getPolyLine(i, lineList, map);
            }

            // 文本
            if (TextEnum.TEXT_NAME.getCode().equals(str)) {
                i = getText(i, lineList, map);
            }

            // 多行文本
            if (MTextEnum.MTEXT_NAME.getCode().equals(str)) {
                i = getMText(i, lineList, map);
            }

            // 插入图元
            if (InsertEnum.INSERT_NAME.getCode().equals(str)) {
                i = getInsert(i, lineList, map);
            }

            //实体结束
            if (FileStructEnum.END_SEC.getCode().equals(str)) {
                break;
            }
        }
        // 实体段结束
    }

    private static int getMText(int i, List<String> lineList, Map<String, List<GeometricObject>> map) {
        String str = null;
        GeometricMText text = new GeometricMText();
        while (true) {
            str = lineList.get(++i);
            // 如果没进入循环需要手动i++
            Boolean flag = false;
            if (MTextEnum.LAYER_NAME.getCode().equals(str)) {
                str = lineList.get(++i);
                text.setGeometricLayer(str);
                flag = true;
            }
            if (MTextEnum.COORDINATE_X.getCode().equals(str)) {
                str = lineList.get(++i);
                text.setX(new BigDecimal(str.trim()));
                flag = true;
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.POINT_NOT_X);
                }
            }
            if (MTextEnum.COORDINATE_Y.getCode().equals(str)) {
                str = lineList.get(++i);
                text.setY(new BigDecimal(str.trim()));
                flag = true;
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.POINT_NOT_Y);
                }
            }
            if (MTextEnum.COORDINATE_Z.getCode().equals(str)) {
                str = lineList.get(++i);
                text.setZ(new BigDecimal(str.trim()));
                flag = true;
//                if (!DecimalCheckUtil.check(str.trim())) {
//                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.POINT_NOT_Z);
//                }
            }
            if (MTextEnum.TEXT_CONTENT.getCode().equals(str)) {
                str = lineList.get(++i);
                text.setTextContent(str);
                flag = true;
                if (!"100".equals(str)) {
                    break;
                }
            }
            // 如果没进入循环需要手动i++
            if (!flag) i++;
        }
        List<GeometricObject> textList = map.get(MTextEnum.MTEXT_NAME.getCode());
        if (textList == null) {
            textList = Lists.newArrayList();
        }
        textList.add(text);
        map.put(MTextEnum.MTEXT_NAME.getCode(), textList);
        return i;
    }

    private static int getText(int i, List<String> lineList, Map<String, List<GeometricObject>> map) {
        String str = null;
        GeometricText text = new GeometricText();
        while (true) {
            str = lineList.get(++i);
            // 如果没进入循环需要手动i++
            Boolean flag = false;
            if (TextEnum.LAYER_NAME.getCode().equals(str)) {
                str = lineList.get(++i);
                text.setGeometricLayer(str);
                flag = true;
            }
            if (TextEnum.COORDINATE_X.getCode().equals(str)) {
                str = lineList.get(++i);
                text.setX(new BigDecimal(str.trim()));
                flag = true;
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.POINT_NOT_X);
                }
            }
            if (TextEnum.COORDINATE_Y.getCode().equals(str)) {
                str = lineList.get(++i);
                text.setY(new BigDecimal(str.trim()));
                flag = true;
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.POINT_NOT_Y);
                }
            }
            if (TextEnum.COORDINATE_Z.getCode().equals(str)) {
                str = lineList.get(++i);
                text.setZ(new BigDecimal(str.trim()));
                flag = true;
//                if (!DecimalCheckUtil.check(str.trim())) {
//                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.POINT_NOT_Z);
//                }
            }
            if (TextEnum.TEXT_CONTENT.getCode().equals(str)) {
                str = lineList.get(++i);
                text.setTextContent(str);
                flag = true;
                if (!"100".equals(str)) {
                    break;
                }
            }
            // 如果没进入循环需要手动i++
            if (!flag) i++;
        }
        List<GeometricObject> textList = map.get(TextEnum.TEXT_NAME.getCode());
        if (textList == null) {
            textList = Lists.newArrayList();
        }
        textList.add(text);
        map.put(TextEnum.TEXT_NAME.getCode(), textList);
        return i;
    }

    private static int getInsert(int i, List<String> lineList, Map<String, List<GeometricObject>> map) {
        String str;
        GeometricPoint point = new GeometricPoint();
        while (true) {
            str = lineList.get(++i);
            if (InsertEnum.LAYER_NAME.getCode().equals(str)) {
                str = lineList.get(++i);
                point.setGeometricLayer(str);
            }
            if (InsertEnum.COORDINATE_X.getCode().equals(str)) {
                str = lineList.get(++i);
                point.setX(new BigDecimal(str.trim()));
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.POINT_NOT_X);
                }
            }
            if (InsertEnum.COORDINATE_Y.getCode().equals(str)) {
                str = lineList.get(++i);
                point.setY(new BigDecimal(str.trim()));
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.POINT_NOT_Y);
                }
            }
            if (InsertEnum.COORDINATE_Z.getCode().equals(str)) {
                str = lineList.get(++i);
                point.setZ(new BigDecimal(str.trim()));
//                if (!DecimalCheckUtil.check(str.trim())) {
//                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.POINT_NOT_Z);
//                }
                break;
            }
        }
        List<GeometricObject> pointList = map.get(PointEnum.POINT_NAME.getCode());
        if (pointList == null) {
            pointList = Lists.newArrayList();
        }
        pointList.add(point);
        map.put(PointEnum.POINT_NAME.getCode(), pointList);
        return i;
    }

    /**
     * 解析点
     *
     * @param i        点开始读取的行数
     * @param lineList 总数据
     * @param map      接收解析的数据map
     * @return 返回点读完的最后行数
     */
    private static int getPoint(int i, List<String> lineList, Map<String, List<GeometricObject>> map) {
        String str = null;
        GeometricPoint point = new GeometricPoint();
        while (true) {
            str = lineList.get(++i);
            if (PointEnum.LAYER_NAME.getCode().equals(str)) {
                str = lineList.get(++i);
                point.setGeometricLayer(str);
            }
            if (PointEnum.COORDINATE_X.getCode().equals(str)) {
                str = lineList.get(++i);
                point.setX(new BigDecimal(str.trim()));
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.POINT_NOT_X);
                }
            }
            if (PointEnum.COORDINATE_Y.getCode().equals(str)) {
                str = lineList.get(++i);
                point.setY(new BigDecimal(str.trim()));
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.POINT_NOT_Y);
                }
            }
            if (PointEnum.COORDINATE_Z.getCode().equals(str)) {
                str = lineList.get(++i);
                point.setZ(new BigDecimal(str.trim()));
//                if (!DecimalCheckUtil.check(str.trim())) {
//                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.POINT_NOT_Z);
//                }
                break;
            }
        }
        List<GeometricObject> pointList = map.get(PointEnum.POINT_NAME.getCode());
        if (pointList == null) {
            pointList = Lists.newArrayList();
        }
        pointList.add(point);
        map.put(PointEnum.POINT_NAME.getCode(), pointList);
        return i;
    }


    /**
     * 获取圆
     *
     * @param i        圆开始读取的行数
     * @param lineList 总数据
     * @param map      接收解析的数据map
     * @return 返回圆读完的最后行数
     */
    private static int getCircle(int i, List<String> lineList, Map<String, List<GeometricObject>> map) {
        String str = null;
        GeometricCircle circle = new GeometricCircle();
        while (true) {
            str = lineList.get(++i);
            //图层名
            if (CircleEnum.LAYER_NAME.getCode().equals(str)) {
                str = lineList.get(++i);
                circle.setGeometricLayer(str);
            }
            //圆心的x坐标
            if (CircleEnum.COORDINATE_X.getCode().equals(str)) {
                str = lineList.get(++i);
                circle.setX(new BigDecimal(str.trim()));
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.CIRCLE_NOT_X);
                }
            }
            //圆心的y坐标
            if (CircleEnum.COORDINATE_Y.getCode().equals(str)) {
                str = lineList.get(++i);
                circle.setY(new BigDecimal(str.trim()));
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.CIRCLE_NOT_Y);
                }
            }
            //圆心的z坐标
            if (CircleEnum.COORDINATE_Z.getCode().equals(str)) {
                str = lineList.get(++i);
                circle.setZ(new BigDecimal(str.trim()));
//                if (!DecimalCheckUtil.check(str.trim())) {
//                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.CIRCLE_NOT_Z);
//                }
            }
            //解析圆的半径
            if (CircleEnum.CIRCULAR_RADIUS.getCode().equals(str)) {
                str = lineList.get(++i);
                circle.setRadius(new BigDecimal(str.trim()));
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.CIRCLE_NOT_RADIUS);
                }
                break;
            }
        }
        List<GeometricObject> circleList = map.get(CircleEnum.CIRCLE_NAME.getCode());
        if (circleList == null) {
            circleList = Lists.newArrayList();
        }
        circleList.add(circle);
        map.put(CircleEnum.CIRCLE_NAME.getCode(), circleList);
        return i;
    }


    /**
     * 获取弧线
     *
     * @param i        弧线开始读取的行数
     * @param lineList 总数据
     * @param map      接收解析的数据map
     * @return 返回弧线读完的最后行数
     */
    private static int getArc(int i, List<String> lineList, Map<String, List<GeometricObject>> map) {
        String str = null;
        GeometricArc arc = new GeometricArc();
        while (true) {
            str = lineList.get(++i);
            //图层名
            if (ArcEnum.LAYER_NAME.getCode().equals(str)) {
                str = lineList.get(++i);
                arc.setGeometricLayer(str);
            }
            //圆弧圆心x坐标
            if (ArcEnum.COORDINATE_X.getCode().equals(str)) {
                str = lineList.get(++i);
                arc.setX(new BigDecimal(str.trim()));
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.ARC_NOT_X);
                }
            }
            //圆弧圆心y坐标
            if (ArcEnum.COORDINATE_Y.getCode().equals(str)) {
                str = lineList.get(++i);
                arc.setY(new BigDecimal(str.trim()));
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.ARC_NOT_Y);
                }
            }
            //圆弧圆心z坐标
            if (ArcEnum.COORDINATE_Z.getCode().equals(str)) {
                str = lineList.get(++i);
                arc.setZ(new BigDecimal(str.trim()));
//                if (!DecimalCheckUtil.check(str.trim())) {
//                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.ARC_NOT_Z);
//                }
            }
            //圆弧半径
            if (ArcEnum.ARC_RADIUS.getCode().equals(str)) {
                str = lineList.get(++i);
                arc.setRadius(new BigDecimal(str.trim()));
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.ARC_NOT_RADIUS);
                }
            }
            //圆弧起始角度
            if (ArcEnum.ARC_START_ANGLE.getCode().equals(str)) {
                str = lineList.get(++i);
                arc.setStartArc(new BigDecimal(str.trim()));
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.ARC_NOT_START_ANGLE);
                }
            }
            //圆弧中止角度
            if (ArcEnum.ARC_END_ANGLE.getCode().equals(str)) {
                str = lineList.get(++i);
                arc.setEndArc(new BigDecimal(str.trim()));
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.ARC_NOT_END_ANGLE);
                }
                break;
            }
        }
        List<GeometricObject> arcList = map.get(ArcEnum.ARC_NAME.getCode());
        if (arcList == null) {
            arcList = Lists.newArrayList();
        }
        arcList.add(arc);
        map.put(ArcEnum.ARC_NAME.getCode(), arcList);
        return i;
    }


    /**
     * 获取线
     *
     * @param i        线开始读取的行数
     * @param lineList 总数据
     * @param map      接收解析的数据map
     * @return 返回直线读完的最后行数
     */
    private static int getLine(int i, List<String> lineList, Map<String, List<GeometricObject>> map) {
        String str = null;
        GeometricLine line = new GeometricLine();
        while (true) {
            str = lineList.get(++i);
            //图层名称
            if (LineEnum.LAYER_NAME.getCode().equals(str)) {
                str = lineList.get(++i);
                line.setGeometricLayer(str);
            }
            //起点x坐标
            if (LineEnum.COORDINATE_X.getCode().equals(str)) {
                str = lineList.get(++i);
                line.setStartX(new BigDecimal(str.trim()));
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.LINE_START_NOT_X);
                }
            }
            //起点y的坐标
            if (LineEnum.COORDINATE_Y.getCode().equals(str)) {
                str = lineList.get(++i);
                line.setStartY(new BigDecimal(str.trim()));
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.LINE_START_NOT_Y);
                }
            }
            //起点z的坐标
            if (LineEnum.COORDINATE_Z.getCode().equals(str)) {
                str = lineList.get(++i);
                line.setStartZ(new BigDecimal(str.trim()));
//                if (!DecimalCheckUtil.check(str.trim())) {
//                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.LINE_START_NOT_Z);
//                }
            }
            //终点的x坐标
            if (LineEnum.LINE_END_X_COORDINATES.getCode().equals(str)) {
                str = lineList.get(++i);
                line.setEndX(new BigDecimal(str.trim()));
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.LINE_END_NOT_X);
                }
            }
            //终点的y坐标
            if (LineEnum.LINE_END_Y_COORDINATES.getCode().equals(str)) {
                str = lineList.get(++i);
                line.setEndY(new BigDecimal(str.trim()));
                if (!DecimalCheckUtil.check(str.trim())) {
                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.LINE_END_NOT_Y);
                }
            }
            //终点的z坐标
            if (LineEnum.LINE_END_Z_COORDINATES.getCode().equals(str)) {
                str = lineList.get(++i);
                line.setEndZ(new BigDecimal(str.trim()));
//                if (!DecimalCheckUtil.check(str.trim())) {
//                    throw new DxfAnalysisException(DxfAnalysisErrorEnum.LINE_END_NOT_Z);
//                }
                break;
            }
        }
        List<GeometricObject> linesList = map.get(LineEnum.LINE_NAME.getCode());
        if (linesList == null) {
            linesList = Lists.newArrayList();
        }
        linesList.add(line);
        map.put(LineEnum.LINE_NAME.getCode(), linesList);
        return i;
    }


    /**
     * 获取多线段
     *
     * @param i        多线段开始读取的行数
     * @param lineList 总数据
     * @param map      接收解析的数据map
     * @return 返回多线段读完最后行数
     */
    private static int getPolyLine(int i, List<String> lineList, Map<String, List<GeometricObject>> map) {
        String str = null;
        GeometricPolyLine polyLine = new GeometricPolyLine();
        List<GeometricVertex> vertices = new ArrayList<>();
        int vertexNum = 0;
        while (true) {
            str = lineList.get(++i);
            //图层名字
            if (PolyLineEnum.LAYER_NAME.getCode().equals(str)) {
                str = lineList.get(++i);
                polyLine.setGeometricLayer(str);
            }
            // 顶点数量
            if (PolyLineEnum.VERTEX_NUM.getCode().equals(str)) {
                str = lineList.get(++i);
                vertexNum = Integer.parseInt(str.trim());
                polyLine.setVertexNum(vertexNum);
            }
            if (PolyLineEnum.COORDINATE_X.getCode().equals(str)) {
                for (int j = 0; j < vertexNum; j++) {
                    GeometricVertex vertex = new GeometricVertex();
                    //顶点的x坐标
                    if (PolyLineEnum.COORDINATE_X.getCode().equals(str)) {
                        str = lineList.get(++i);
                        if (DecimalCheckUtil.check(str.trim())) {
                            vertex.setX(new BigDecimal(str.trim()));
                            str = lineList.get(++i);
                        } else {
                            break;
                        }
                    }
                    //顶点的y坐标
                    if (PolyLineEnum.COORDINATE_Y.getCode().equals(str)) {
                        str = lineList.get(++i);
                        vertex.setY(new BigDecimal(str.trim()));
                        if (DecimalCheckUtil.check(str.trim())) {
                            vertex.setY(new BigDecimal(str.trim()));
                            str = lineList.get(++i);
                        }
                        vertices.add(vertex);
                    }
                    // 最后一个点位 重置 flag 字段为 -1
                    if (j == vertexNum - 1) {
                        vertexNum = -1;
                    }
                }
            }
            // 顶点结束
            if (vertexNum < 0) {
                break;
            }
        }
        polyLine.setVertexList(vertices);
        List<GeometricObject> polyLines = map.get(PolyLineEnum.POLYLINE_NAME.getCode());
        if (polyLines == null) {
            polyLines = Lists.newArrayList();
        }
        polyLines.add(polyLine);
        map.put(PolyLineEnum.POLYLINE_NAME.getCode(), polyLines);
        return i;
    }

    /**
     * 读取所有的行
     *
     * @param reader BufferedReader
     * @return 返回文件的所有数据，以每一行数据为一个item
     * @throws IOException IO异常
     */
    private static List<String> readAllLine(BufferedReader reader) throws IOException {
        List<String> list = Lists.newArrayList();
        String line = null;
        while ((line = reader.readLine()) != null) {
            list.add(line.trim());
        }
        return list;
    }


}