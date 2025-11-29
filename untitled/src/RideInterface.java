/**
 * 游乐设施接口：定义游乐设施的核心行为规范
 * 强制Ride类实现统一功能，体现抽象与多态
 */
public interface RideInterface {
    // 队列管理方法
    void addVisitorToQueue(Visitor visitor);    // 添加游客到等待队列
    void removeVisitorFromQueue();              // 从队列移除首个游客
    void printQueue();                          // 打印等待队列

    // 乘坐历史管理方法
    void addVisitorToHistory(Visitor visitor);  // 添加游客到乘坐历史
    boolean checkVisitorFromHistory(Visitor visitor); // 检查游客是否在历史中
    int numberOfVisitors();                     // 返回历史游客总数
    void printRideHistory();                    // 打印乘坐历史（需用Iterator）

    // 运行周期方法
    void runOneCycle();                         // 运行一次游乐设施周期
}