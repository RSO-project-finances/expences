package si.fri.rso.samples.imagecatalog.lib;

import java.time.Instant;
import java.util.Date;

public class Expenses {

    private Integer expenseId;
    private String kind;
    private Date date_occurrence;
    private String description;
    private Double price;

    public String getKind() {
        return kind;
    }

    public void setKind(String kind) {
        this.kind = kind;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setDate_occurrence(Date date_occurrence) {
        this.date_occurrence = date_occurrence;
    }

    public Date getDate_occurrence() {
        return date_occurrence;
    }

    public Integer getExpenseId() {
        return expenseId;
    }

    public void setExpenseId(Integer expenseId) {
        this.expenseId = expenseId;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    @Override
    public String toString() {
        return String.format("%d\n%s\n%s\n%.02f", expenseId, kind, date_occurrence.toString(), price);
    }
}
