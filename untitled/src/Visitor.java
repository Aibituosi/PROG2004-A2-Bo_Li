/**
 * 游客类：继承Person，封装主题公园游客特有属性与行为
 * 用于跟踪游客信息、乘坐记录
 */
public class Visitor extends Person {
    // 特有实例变量（满足至少2个要求）
    private String visitorId;     // 游客编号（如"VIS001"）
    private String membershipType;// 会员类型（如"普通会员"、"VIP"、"临时游客"）

    // 无参构造器
    public Visitor() {}

    // 带参构造器（初始化父类+子类属性）
    public Visitor(String id, String name, int age, String visitorId, String membershipType) {
        super(id, name, age);
        this.visitorId = visitorId;
        this.membershipType = membershipType;
    }

    // Getter/Setter
    public String getVisitorId() {
        return visitorId;
    }

    public void setVisitorId(String visitorId) {
        this.visitorId = visitorId;
    }

    public String getMembershipType() {
        return membershipType;
    }

    public void setMembershipType(String membershipType) {
        this.membershipType = membershipType;
    }

    // 重写toString：格式化输出游客完整信息（多态体现）
    @Override
    public String toString() {
        return String.format("游客[身份证号：%s，姓名：%s，年龄：%d，游客编号：%s，会员类型：%s]",
                getId(), getName(), getAge(), visitorId, membershipType);
    }
}