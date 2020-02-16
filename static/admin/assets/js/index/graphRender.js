$(function () {
    drawLayer02Label($("#layer02_01 canvas").get(0), "接入终端数量", 80, 200);
    drawLayer02Label($("#layer02_02 canvas").get(0), "今日接入数据数量", 80, 300);
    drawLayer02Label($("#layer02_03 canvas").get(0), "今日新增存储数据", 80, 400);
    drawLayer02Label($("#layer02_04 canvas").get(0), "总存储数据", 50, 200);
    drawLayer02Label($("#layer02_05 canvas").get(0), "当前任务个数", 40, 200);
    drawLayer02Label($("#layer02_06 canvas").get(0), "当前集群数", 50, 200);

    renderLegend();

    //饼状图
    renderChartBar01();
    //renderChartBar02();

    //存储
    renderLayer03Right();

    //30天日均线流量趋势
    renderLayer04Left();

    //集群性能
    renderLayer04Right();
});