//package entity;
//
//import lombok.Getter;
//import lombok.NoArgsConstructor;
//import lombok.Setter;
//
//import javax.persistence.*;
//import java.io.Serializable;
//
//@Getter
//@Setter
//@NoArgsConstructor
//@Entity
//@Table(name="department")
//public class Department1 implements Serializable {
//    @Id
//    @Column(name="department_id")
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private int id;
//
//    @Column(name="name")
//    private String name;
//
//    @Column(name="status")
//    private Boolean status;
//
//    @Override
//    public String toString() {
//        return "Department1{" +
//                "id=" + id +
//                ", name='" + name + '\'' +
//                ", status=" + status +
//                '}';
//    }
//}
