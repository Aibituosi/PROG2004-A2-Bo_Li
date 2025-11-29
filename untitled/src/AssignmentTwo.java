/**
 * 主类：包含所有作业要求的演示方法（Part 3-Part 7）
 * 程序入口，验证所有功能的正确性
 */
public class AssignmentTwo {
    public static void main(String[] args) {
        System.out.println("=== PROG2004 A2 主题公园管理系统（PRVMS）演示 ===\n");

        // 依次运行各Part演示方法
        AssignmentTwo demo = new AssignmentTwo();
        demo.partThree();
        demo.partFourA();
        demo.partFourB();
        demo.partFive();
        demo.partSix();
        demo.partSeven();

        System.out.println("\n=== 所有功能演示完成 ===");
    }

    // ------------------------------  Part 3：等待队列演示  ------------------------------
    public void partThree() {
        System.out.println("==================================== Part 3：等待队列管理 ====================================");
        // 创建操作员
        Employee operator = new Employee("440101199001011234", "张三", 30, "EMP001", "过山车操作员");
        // 创建游乐设施
        Ride rollerCoaster = new Ride("RC001", "极速过山车", operator, 4);

        // 添加5名游客到队列
        rollerCoaster.addVisitorToQueue(new Visitor("440101200001011234", "李四", 25, "VIS001", "VIP"));
        rollerCoaster.addVisitorToQueue(new Visitor("440101200101011234", "王五", 22, "VIS002", "普通会员"));
        rollerCoaster.addVisitorToQueue(new Visitor("440101200201011234", "赵六", 18, "VIS003", "临时游客"));
        rollerCoaster.addVisitorToQueue(new Visitor("440101200301011234", "孙七", 30, "VIS004", "VIP"));
        rollerCoaster.addVisitorToQueue(new Visitor("440101200401011234", "周八", 28, "VIS005", "普通会员"));

        // 打印队列
        rollerCoaster.printQueue();

        // 移除1名游客
        rollerCoaster.removeVisitorFromQueue();

        // 打印移除后的队列
        System.out.println("\n移除队首游客后：");
        rollerCoaster.printQueue();
    }

    // ------------------------------  Part 4A：乘坐历史演示  ------------------------------
    public void partFourA() {
        System.out.println("\n==================================== Part 4A：乘坐历史管理 ====================================");
        Ride waterRide = new Ride("WR001", "激流勇进", null, 6);

        // 添加5名游客到历史
        Visitor v1 = new Visitor("440101200002021234", "吴九", 24, "VIS006", "普通会员");
        Visitor v2 = new Visitor("440101200003031234", "郑十", 26, "VIS007", "VIP");
        Visitor v3 = new Visitor("440101200004041234", "王十一", 21, "VIS008", "临时游客");
        Visitor v4 = new Visitor("440101200005051234", "李十二", 29, "VIS009", "VIP");
        Visitor v5 = new Visitor("440101200006061234", "张十三", 23, "VIS010", "普通会员");

        waterRide.addVisitorToHistory(v1);
        waterRide.addVisitorToHistory(v2);
        waterRide.addVisitorToHistory(v3);
        waterRide.addVisitorToHistory(v4);
        waterRide.addVisitorToHistory(v5);

        // 检查游客是否在历史中
        boolean isInHistory = waterRide.checkVisitorFromHistory(v2);
        System.out.printf("%n游客【%s】是否在乘坐历史中：%s%n", v2.getName(), isInHistory ? "是" : "否");

        // 打印历史游客总数
        System.out.printf("乘坐历史游客总数：%d人%n", waterRide.numberOfVisitors());

        // 打印乘坐历史（使用Iterator）
        waterRide.printRideHistory();
    }

    // ------------------------------  Part 4B：历史排序演示  ------------------------------
    public void partFourB() {
        System.out.println("\n==================================== Part 4B：乘坐历史排序 ====================================");
        Ride ferrisWheel = new Ride("FW001", "摩天轮", null, 8);

        // 添加5名游客（年龄、会员类型随机）
        ferrisWheel.addVisitorToHistory(new Visitor("440101200007071234", "刘十四", 22, "VIS011", "普通会员"));
        ferrisWheel.addVisitorToHistory(new Visitor("440101999908081234", "陈十五", 27, "VIS012", "临时游客"));
        ferrisWheel.addVisitorToHistory(new Visitor("440101199809091234", "杨十六", 28, "VIS013", "VIP"));
        ferrisWheel.addVisitorToHistory(new Visitor("440101200110101234", "黄十七", 20, "VIS014", "VIP"));
        ferrisWheel.addVisitorToHistory(new Visitor("440101200011111234", "周十八", 22, "VIS015", "普通会员"));

        // 排序前打印
        System.out.println("\n排序前的乘坐历史：");
        ferrisWheel.printRideHistory();

        // 排序
        ferrisWheel.sortRideHistory();

        // 排序后打印
        System.out.println("\n排序后的乘坐历史（按年龄升序，会员类型降序）：");
        ferrisWheel.printRideHistory();
    }

    // ------------------------------  Part 5：运行周期演示  ------------------------------
    public void partFive() {
        System.out.println("\n==================================== Part 5：运行游乐设施周期 ====================================");
        // 创建操作员
        Employee operator = new Employee("440101198912121234", "马十九", 36, "EMP002", "激流勇进操作员");
        // 创建游乐设施（单周期最大载客量3人）
        Ride logFlume = new Ride("LF001", "原木漂流", operator, 3);

        // 添加10名游客到队列
        for (int i = 0; i < 10; i++) {
            String visitorId = String.format("VIS%03d", 16 + i);
            String membership = (i % 3 == 0) ? "VIP" : (i % 3 == 1) ? "普通会员" : "临时游客";
            logFlume.addVisitorToQueue(new Visitor(
                    String.format("4401012000%02d%02d1234", (i + 1)/10 + 1, (i + 1)%10 + 1),
                    "游客" + (i + 16),
                    18 + (i % 12), // 年龄18-30岁
                    visitorId,
                    membership
            ));
        }

        // 打印运行前队列
        System.out.println("\n运行前的等待队列：");
        logFlume.printQueue();

        // 运行一次周期
        logFlume.runOneCycle();

        // 打印运行后队列
        System.out.println("\n运行后的等待队列：");
        logFlume.printQueue();

        // 打印乘坐历史
        logFlume.printRideHistory();
    }

    // ------------------------------  Part 6：导出历史到文件  ------------------------------
    public void partSix() {
        System.out.println("\n==================================== Part 6：导出乘坐历史到文件 ====================================");
        Ride hauntedHouse = new Ride("HH001", "鬼屋", null, 5);

        // 添加5名游客到历史
        hauntedHouse.addVisitorToHistory(new Visitor("440101200012121234", "朱二十", 25, "VIS026", "VIP"));
        hauntedHouse.addVisitorToHistory(new Visitor("440101200013131234", "胡二一", 23, "VIS027", "普通会员"));
        hauntedHouse.addVisitorToHistory(new Visitor("440101200014141234", "林二二", 27, "VIS028", "临时游客"));
        hauntedHouse.addVisitorToHistory(new Visitor("440101200015151234", "郭二三", 24, "VIS029", "VIP"));
        hauntedHouse.addVisitorToHistory(new Visitor("440101200016161234", "何二四", 22, "VIS030", "普通会员"));

        // 导出到CSV文件（路径可根据实际环境修改）
        String exportPath = "ride_history_haunted_house.csv";
        hauntedHouse.exportRideHistory(exportPath);
    }

    // ------------------------------  Part 7：从文件导入历史  ------------------------------
    public void partSeven() {
        System.out.println("\n==================================== Part 7：从文件导入乘坐历史 ====================================");
        Ride importedRide = new Ride("IM001", "导入测试设施", null, 5);

        // 导入Part 6导出的文件
        String importPath = "ride_history_haunted_house.csv";
        importedRide.importRideHistory(importPath);

        // 验证导入结果
        System.out.printf("%n导入后游客总数：%d人%n", importedRide.numberOfVisitors());
        importedRide.printRideHistory();
    }
}