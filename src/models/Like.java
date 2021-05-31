package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;

@Table(name="likes")
@NamedQueries({
    @NamedQuery(
            name = "getLikesCount",
            query = "SELECT count(l) FROM Like AS l WHERE l.employee_id = :employee_id"
            ),
    @NamedQuery(
            name = "searchLike",
            query = "SELECT count(l) FROM Like AS l WHERE l.report_id = :report_id AND l.employee_id = :employee_id"
            ),
    @NamedQuery(
            name = "searchLikeData",
            query = "SELECT l FROM Like AS l WHERE l.report_id = :report_id"
            ),
})
@Entity
public class Like {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private Employee employee_id;

    @Column(name = "report_id")
    private Integer report_id;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getEmployee_id() {
        return employee_id;
    }

    public void setEmployee_id(Employee employee_id) {
        this.employee_id = employee_id;
    }

    public Integer getReport_id() {
        return report_id;
    }

    public void setReport_id(Integer report_id) {
        this.report_id = report_id;
    }


}
