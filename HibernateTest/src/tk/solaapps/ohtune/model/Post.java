package tk.solaapps.ohtune.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="post")
public class Post{
    private Long id;
    private String name;
    private String remark;
    private long section;
    
    @Id
    @Column(name="id")
    public Long getId() {
            return id;
    }
    public void setId(Long id) {
            this.id = id;
    }
    
    @Column(name="name")
    public String getName() {
            return name;
    }
    public void setName(String name) {
            this.name = name;
    }
    
    
    @Column(name="remark")
    public String getRemark() {
            return remark;
    }
    public void setRemark(String remark) {
            this.remark = remark;
    }
    
    @Column(name="section")
    public long getSection() {
            return section;
    }
    public void setSection(long section) {
            this.section = section;
    }
}