/**
 * 员工类：继承Person，封装主题公园员工特有属性与行为
 * 用于跟踪游乐设施操作员信息
 */
public class Employee extends Person {
    // 特有实例变量（满足至少2个要求）
    private String employeeId;  // 员工编号（如"EMP001"）
    private String position;    // 岗位（如"过山车操作员"、"安全员"）

    // 无参构造器
    public Employee() {}

    // 带参构造器（初始化父类+子类属性）
    public Employee(String id, String name, int age, String employeeId, String position) {
        super(id, name, age);
        this.employeeId = employeeId;
        this.position = position;
    }

    // Getter/Setter
    public String getEmployeeId() {
        return employeeId;
    }

    public void setEmployeeId(String employeeId) {
        this.employeeId = employeeId;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    // 重写toString：格式化输出员工完整信息（多态体现）
    @Override
    public String toString() {
        return String.format("员工[身份证号：%s，姓名：%s，年龄：%d，员工编号：%s，岗位：%s]",
                getId(), getName(), getAge(), employeeId, position);
    }
}