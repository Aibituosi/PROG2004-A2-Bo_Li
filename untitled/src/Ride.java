import java.io.*;
import java.util.*;

/**
 * 游乐设施类：实现RideInterface，管理游乐设施的队列、历史、运行、文件I/O
 * 高内聚设计：独立处理自身所有业务逻辑
 */
public class Ride implements RideInterface {
    // 实例变量（满足至少3个要求，含Employee类型）
    private String rideId;          // 设施编号（如"RC001"）
    private String rideName;        // 设施名称（如"极速过山车"）
    private Employee operator;      // 操作员（Employee类型，控制设施是否可运行）
    private int maxRider;           // 单周期最大载客量
    private int numOfCycles;        // 已运行周期数（默认0）
    private Queue<Visitor> waitingQueue;  // 等待队列（FIFO，用LinkedList实现）
    private LinkedList<Visitor> rideHistory; // 乘坐历史（用LinkedList实现，便于迭代）

    // 无参构造器（初始化集合与默认值）
    public Ride() {
        this.waitingQueue = new LinkedList<>();
        this.rideHistory = new LinkedList<>();
        this.numOfCycles = 0;
        this.maxRider = 1; // 默认单周期至少1人
    }

    // 带参构造器（初始化所有属性）
    public Ride(String rideId, String rideName, Employee operator, int maxRider) {
        this(); // 调用无参构造器初始化集合
        this.rideId = rideId;
        this.rideName = rideName;
        this.operator = operator;
        // 验证最大载客量合法性
        this.maxRider = maxRider >= 1 ? maxRider : 1;
    }

    // Getter/Setter
    public String getRideId() {
        return rideId;
    }

    public void setRideId(String rideId) {
        this.rideId = rideId;
    }

    public String getRideName() {
        return rideName;
    }

    public void setRideName(String rideName) {
        this.rideName = rideName;
    }

    public Employee getOperator() {
        return operator;
    }

    public void setOperator(Employee operator) {
        this.operator = operator;
    }

    public int getMaxRider() {
        return maxRider;
    }

    public void setMaxRider(int maxRider) {
        if (maxRider >= 1) {
            this.maxRider = maxRider;
        } else {
            System.out.println("错误：单周期最大载客量必须≥1");
        }
    }

    public int getNumOfCycles() {
        return numOfCycles;
    }

    // ------------------------------  Part 3：等待队列管理  ------------------------------
    @Override
    public void addVisitorToQueue(Visitor visitor) {
        if (visitor != null) {
            waitingQueue.offer(visitor); // Queue的offer方法添加元素（失败返回false）
            System.out.printf("成功：游客【%s】已加入【%s】的等待队列%n", visitor.getName(), rideName);
        } else {
            System.out.println("错误：无法添加空游客到队列");
        }
    }

    @Override
    public void removeVisitorFromQueue() {
        if (waitingQueue.isEmpty()) {
            System.out.printf("失败：【%s】的等待队列为空，无游客可移除%n", rideName);
            return;
        }
        Visitor removed = waitingQueue.poll(); // 移除并返回队首元素
        System.out.printf("成功：从【%s】队列移除游客【%s】%n", rideName, removed.getName());
    }

    @Override
    public void printQueue() {
        System.out.printf("%n【%s】等待队列（共%d人）：%n", rideName, waitingQueue.size());
        if (waitingQueue.isEmpty()) {
            System.out.println("队列无游客");
            return;
        }
        // 按加入顺序打印（Queue的迭代器是FIFO顺序）
        int index = 1;
        for (Visitor visitor : waitingQueue) {
            System.out.printf("%d. %s%n", index++, visitor);
        }
    }

    // ------------------------------  Part 4A：乘坐历史管理  ------------------------------
    @Override
    public void addVisitorToHistory(Visitor visitor) {
        if (visitor != null) {
            rideHistory.add(visitor);
            System.out.printf("成功：游客【%s】已添加到【%s】的乘坐历史%n", visitor.getName(), rideName);
        } else {
            System.out.println("错误：无法添加空游客到乘坐历史");
        }
    }

    @Override
    public boolean checkVisitorFromHistory(Visitor visitor) {
        if (visitor == null || rideHistory.isEmpty()) {
            return false;
        }
        // 按游客编号判断（唯一标识）
        for (Visitor v : rideHistory) {
            if (v.getVisitorId().equals(visitor.getVisitorId())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public int numberOfVisitors() {
        return rideHistory.size();
    }

    @Override
    public void printRideHistory() {
        System.out.printf("%n【%s】乘坐历史（共%d人）：%n", rideName, rideHistory.size());
        if (rideHistory.isEmpty()) {
            System.out.println("暂无乘坐记录");
            return;
        }
        // 必须使用Iterator遍历（作业强制要求）
        Iterator<Visitor> iterator = rideHistory.iterator();
        int index = 1;
        while (iterator.hasNext()) {
            Visitor visitor = iterator.next();
            System.out.printf("%d. %s%n", index++, visitor);
        }
    }

    // ------------------------------  Part 4B：乘坐历史排序  ------------------------------
    /**
     * 按指定规则排序乘坐历史（使用Comparator，不使用Comparable）
     * 排序规则：先按年龄升序，再按会员类型降序（VIP > 普通会员 > 临时游客）
     */
    public void sortRideHistory() {
        if (rideHistory.isEmpty()) {
            System.out.printf("警告：【%s】的乘坐历史为空，无需排序%n", rideName);
            return;
        }
        // 使用自定义Comparator排序
        Collections.sort(rideHistory, new VisitorComparator());
        System.out.printf("成功：【%s】的乘坐历史已排序%n", rideName);
    }

    // ------------------------------  Part 5：运行游乐设施周期  ------------------------------
    @Override
    public void runOneCycle() {
        // 检查是否有操作员
        if (operator == null) {
            System.out.printf("失败：【%s】未分配操作员，无法运行%n", rideName);
            return;
        }
        // 检查等待队列是否为空
        if (waitingQueue.isEmpty()) {
            System.out.printf("失败：【%s】的等待队列为空，无法运行%n", rideName);
            return;
        }

        System.out.printf("%n开始运行【%s】第%d周期（最大载客量：%d人）...%n", rideName, numOfCycles + 1, maxRider);
        int ridersToTake = Math.min(maxRider, waitingQueue.size()); // 实际载客量（取maxRider和队列大小的较小值）

        // 从队列移除游客，添加到历史
        for (int i = 0; i < ridersToTake; i++) {
            Visitor visitor = waitingQueue.poll();
            addVisitorToHistory(visitor);
            System.out.printf("已搭载游客：%s%n", visitor.getName());
        }

        numOfCycles++; // 周期数+1
        System.out.printf("成功：【%s】第%d周期运行完成%n", rideName, numOfCycles);
    }

    // ------------------------------  Part 6：导出乘坐历史到CSV文件  ------------------------------
    /**
     * 导出乘坐历史到CSV文件，每行存储1个游客信息（逗号分隔）
     * 格式：身份证号,姓名,年龄,游客编号,会员类型
     */
    public void exportRideHistory(String filePath) {
        if (rideHistory.isEmpty()) {
            System.out.printf("警告：【%s】的乘坐历史为空，无需导出%n", rideName);
            return;
        }

        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            // 遍历历史，写入文件
            for (Visitor visitor : rideHistory) {
                String line = String.join(",",
                        visitor.getId(),
                        visitor.getName(),
                        String.valueOf(visitor.getAge()),
                        visitor.getVisitorId(),
                        visitor.getMembershipType()
                );
                writer.write(line);
                writer.newLine(); // 换行
            }
            System.out.printf("成功：【%s】的乘坐历史已导出到文件：%s%n", rideName, filePath);
        } catch (IOException e) {
            System.out.printf("失败：导出【%s】乘坐历史时出错 - %s%n", rideName, e.getMessage());
        }
    }

    // ------------------------------  Part 7：从CSV文件导入乘坐历史  ------------------------------
    /**
     * 从CSV文件导入游客信息，添加到乘坐历史
     * 兼容Part 6的导出格式
     */
    public void importRideHistory(String filePath) {
        File file = new File(filePath);
        if (!file.exists()) {
            System.out.printf("失败：文件不存在 - %s%n", filePath);
            return;
        }

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String line;
            int importedCount = 0;
            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty()) continue; // 跳过空行

                // 拆分CSV字段（按逗号分隔）
                String[] fields = line.split(",");
                if (fields.length != 5) {
                    System.out.printf("警告：跳过格式错误的行 - %s%n", line);
                    continue;
                }

                // 解析字段，创建Visitor对象
                String id = fields[0];
                String name = fields[1];
                int age = Integer.parseInt(fields[2]);
                String visitorId = fields[3];
                String membershipType = fields[4];

                Visitor visitor = new Visitor(id, name, age, visitorId, membershipType);
                rideHistory.add(visitor);
                importedCount++;
            }
            System.out.printf("成功：从文件【%s】导入%d名游客到【%s】的乘坐历史%n", filePath, importedCount, rideName);
        } catch (NumberFormatException e) {
            System.out.printf("失败：导入时年龄格式错误 - %s%n", e.getMessage());
        } catch (IOException e) {
            System.out.printf("失败：导入【%s】乘坐历史时出错 - %s%n", rideName, e.getMessage());
        }
    }
}