package uk.gov.justice.digital.hmpps.communityApiWiremock.dao.entity;


import java.util.List;
import java.util.Set;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

@Data
@Entity
@DynamicUpdate
@Table(name = "team")
public class TeamEntity {

  @Id
  private Long id;

  // PDU == borough
  private String boroughCode;
  private String boroughDescription;

  // LAU == district
  private String districtCode;
  private String districtDescription;

  // Team
  private String teamCode;
  private String teamDescription;
  private String teamTelephone;

  @ManyToMany
  @JoinTable(
      name = "staff_team",
      joinColumns = { @JoinColumn(name = "team_id") },
      inverseJoinColumns = { @JoinColumn(name = "staff_id") }
  )
  private List<StaffEntity> staff;

  @OneToMany(mappedBy="team")
  private List<OffenderEntity> managedOffenders;
}
