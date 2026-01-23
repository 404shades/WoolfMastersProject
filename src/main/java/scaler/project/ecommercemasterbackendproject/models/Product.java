package scaler.project.ecommercemasterbackendproject.models;


import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Entity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

@Data
@Entity
public class Product extends BaseModel{
    private Double price;
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    // owner of the product, always set from the logged in user.
    // ignored in json so a client can't send someone else as the vendor,
    // and so the whole User (with password hash) is never sent back
    @JsonIgnore
    @ManyToOne
    @JoinColumn(name = "vendor_id")
    private User vendor;

    // only these two vendor fields are shown in the response
    public Long getVendorId() {
        return vendor == null ? null : vendor.getId();
    }

    public String getVendorName() {
        return vendor == null ? null : vendor.getName();
    }
}
