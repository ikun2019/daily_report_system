package models;

import java.sql.Timestamp;

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

@Table(name="follows")
@NamedQueries({
    @NamedQuery(
            name = "getAllFollowers",
            query = "SELECT f.followed_Code From Follow AS f WHERE f.follow_id = :login_employee"
            ),
    @NamedQuery(
            name = "getFollower",
            query = "SELECT f From Follow AS f WHERE f.follow_id = :login_employee AND f.followed_Code = :employeeCode"
            ),
    @NamedQuery(
            name = "getFollowersCount",
            query = "SELECT COUNT(f) FROM Follow AS f"
            ),
})
@Entity
public class Follow {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne
    @JoinColumn(name = "follow_id", nullable = false)
    private Employee follow_id;

    @Column(name = "followed_Code", nullable = false)
    private String followed_Code;

    @Column(name = "created_at", nullable = false)
    private Timestamp created_at;

    @Column(name = "updated_at", nullable = false)
    private Timestamp updated_at;


    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Employee getFollow_id() {
        return follow_id;
    }

    public void setFollow_id(Employee follow_id) {
        this.follow_id = follow_id;
    }

    public String getFollowed_Code() {
        return followed_Code;
    }

    public void setFollowed_Code(String followed_Code) {
        this.followed_Code = followed_Code;
    }

    public Timestamp getCreated_at() {
        return created_at;
    }

    public void setCreated_at(Timestamp created_at) {
        this.created_at = created_at;
    }

    public Timestamp getUpdated_at() {
        return updated_at;
    }

    public void setUpdated_at(Timestamp updated_at) {
        this.updated_at = updated_at;
    }


}
