package si.fri.rso.samples.imagecatalog.models.entities;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@Entity
@Table(name = "expenses")
@NamedQueries(value =
        {
                @NamedQuery(name = "ExpensesEntity.getAll",
                        query = "SELECT im FROM ExpensesEntity im")
        })
public class ExpensesEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "kind")
    private String kind;

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "date_occurrence")
    private Date date_occurrence;

    @Column(name = "description")
    private String description;

    @Column(name = "price")
    private Double price;

    public Integer getExpenseId() {
        return id;
    }

    public void setExpenseId(Integer id) {
        this.id = id;
    }

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public Date getDate_occurrence() {
        return date_occurrence;
    }

    public void setDate_occurrence(Date date_occurrence) {
        this.date_occurrence = date_occurrence;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }
}