package uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity;

import java.util.List;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate
@Table(name = "staff")
public class StaffEntity {

  @Id
  private Long id;

  private String username;
  private Long staffIdentifier;
  private String staffCode;
  private String email;
  private String telephoneNumber;
  private String staffForenames;
  private String staffSurname;
  private String probationAreaCode;
  private String probationAreaDescription;

  @OneToMany(mappedBy="staff")
  private List<OffenderEntity> managedOffenders;

  @ManyToMany(mappedBy = "staff")
  private List<TeamEntity> teams;
}
