package com.google.rolecall.models;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.google.rolecall.Constants;
import com.google.rolecall.jsonobjects.UserInfo;
import com.google.rolecall.restcontrollers.exceptionhandling.RequestExceptions.InvalidParameterException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Optional;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import org.hibernate.annotations.NaturalId;

/* Basic user information. */
@Entity
@Table
public class User {
  
  @Id
  @GeneratedValue(strategy=GenerationType.AUTO)
  private Integer id;

  @Column(nullable = false)
  private String firstName;

  @Column(nullable = false)
  private String lastName;

  @NaturalId
  @Column(nullable = false, unique = true)
  private String email;

  @Basic
  @Temporal(TemporalType.DATE)
  @JsonFormat(pattern="MM-dd-yyyy")
  private Calendar dateJoined;

  @Basic
  private String emergencyContactName;

  @Basic
  private String emergencyContactNumber;

  @Basic
  private String comments;

  @Basic
  private String role;

  @Column(nullable = false)
  private Boolean isActive;

  // Permissions with defaults
  @Column(nullable = false)
  private boolean canLogin = true;
  @Column(nullable = false)
  private boolean admin = false;
  @Column(nullable = false)
  private boolean notifications = true;
  @Column(nullable = false)
  private boolean managePerformances = false;
  @Column(nullable = false)
  private boolean manageCasts = false;
  @Column(nullable = false)
  private boolean managePieces = false;
  @Column(nullable = false)
  private boolean manageRoles = false;
  @Column(nullable = false)
  private boolean manageRules = false;

  public Integer getId() {
    return id;
  }

  public String getFirstName() {
    return firstName;
  }

  public String getLastName() {
    return lastName;
  }

  public String getEmail() {
    return email;
  }

  public Optional<Calendar> getDateJoined() {
    return dateJoined == null ? Optional.empty(): Optional.of(dateJoined);
  }

  public String getEmergencyContactName() {
    return emergencyContactName == null ? "" : emergencyContactName;
  }

  public String getEmergencyContactNumber() {
    return emergencyContactNumber == null ? "" : emergencyContactNumber;
  }

  public String getComments() {
    return comments == null ? "" : comments;
  }

  public String getRole() {
    return role == null ? "" : role;
  }

  public boolean isActive() {
    return isActive;
  }

  public boolean canLogin() {
    return canLogin;
  }

  public boolean isAdmin() {
    return admin;
  }

  public boolean recievesNotifications() {
    return notifications;
  }
  
  public boolean canManagePerformances() {
    return managePerformances;
  }
  
  public boolean canManageCasts() {
    return manageCasts;
  }
  
  public boolean canManagePieces() {
    return managePieces;
  }
  
  public boolean canManageRoles() {
    return manageRoles;
  }
  
  public boolean canManageRules() {
    return manageRules;
  }

  public void addPerformanceCastMember(PerformanceCastMember member) {
    member.setUser(this);
  }

  public void removePerformanceCastMember(PerformanceCastMember member) {
    member.setUser(null);
  }

  public void addUnavailability(Unavailability unavailable) {
    unavailable.setUser(this);
  }

  public void removeUnavailability(Unavailability unavailable) {
    unavailable.setUser(null);
  }

  public String[] getRoles() {
    List<Boolean> permissions = new ArrayList<>();
    permissions.add(isAdmin()); // ADMIN
    permissions.add(isAdmin() || canLogin()); // LOGIN
    permissions.add(recievesNotifications()); // NOTIFICATIONS
    permissions.add(isAdmin() || canManagePerformances()); // MANGAGE_PERFORMANCES
    permissions.add(isAdmin() || canManageCasts()); // MANAGE_CASTS
    permissions.add(isAdmin() || canManagePieces()); // MANAGE_PIECES
    permissions.add(isAdmin() || canManageRoles()); // MANAGE_ROLES
    permissions.add(isAdmin() || canManageRules()); // MANAGE_RULES

    ArrayList<String> roles = new ArrayList<>();
    for(int i = 0; i < Constants.Roles.ROLES.length; i++) {
      if (permissions.get(i)) {
        roles.add(Constants.Roles.ROLES[i]);
      }
    }
    String[] out = new String[roles.size()];
    return roles.toArray(out);
  }

  public UserInfo toUserInfo() {
    return UserInfo.newBuilder()
        .setId(id)
        .setFirstName(firstName)
        .setLastName(lastName)
        .setEmail(email)
        .setDateJoined(dateJoined)
        .setEmergencyContactName(getEmergencyContactName())
        .setEmergencyContactNumber(getEmergencyContactNumber())
        .setComments(getComments())
        .setRole(getRole())
        .setIsActive(isActive)
        .setCanLogin(canLogin)
        .setAdmin(admin)
        .setNotifications(notifications)
        .setManagePerformances(managePerformances)
        .setManageCasts(manageCasts)
        .setManagePieces(managePieces)
        .setManageRoles(manageRoles)
        .setManageRules(manageRules)
        .build();
  }

  public Builder toBuilder() {
    return new Builder(this);
  }

  // Required for spring reflective CRUD Repository call
  public User() {
  }

  public static Builder newBuilder() {
    return new Builder();
  }

  public static class Builder {
    private User user;
    private Integer id;
    private String firstName;
    private String lastName;
    private String email;
    private Calendar dateJoined;
    private String emergencyContactName;
    private String emergencyContactNumber;
    private String comments;
    private String role;
    private Boolean isActive = true;
    private Boolean canLogin = false;
    private Boolean admin = false;
    private Boolean notifications = true;
    private Boolean managePerformances = false;
    private Boolean manageCasts = false;
    private Boolean managePieces = false;
    private Boolean manageRoles = false;
    private Boolean manageRules = false;

    public Builder setFirstName(String name) {
      if(name != null) {
        this.firstName = name;
      }
      return this;
    }
  
    public Builder setLastName(String name) {
      if(name != null) {
        this.lastName = name;
      }
      return this;
    }
  
    public Builder setEmail(String email) {
      if(email != null) {
        this.email = email.toLowerCase();
      }
      return this;
    }
  
    public Builder setDateJoined(Calendar dateJoined) {
      if(dateJoined != null) {
        this.dateJoined = dateJoined;
      }
      return this;
    }
  
    public Builder setEmergencyContactName(String emergencyContactName) {
      if(emergencyContactName != null) {
        this.emergencyContactName = emergencyContactName;
      }
      return this;
    }
  
    public Builder setEmergencyContactNumber(String emergencyContactNumber) {
      if(emergencyContactNumber != null) {
        this.emergencyContactNumber = emergencyContactNumber;
      }
      return this;
    }
  
    public Builder setComments(String comments) {
      if(comments != null) {
        this.comments = comments;
      }
      return this;
    }

    public Builder setRole(String role) {
      if(role != null) {
        this.role = role;
      }
      return this;
    }
  
    public Builder setIsActive(Boolean isActive) {
      if(isActive != null) {
        this.isActive = isActive;
      }
      return this;
    }
  
    public Builder setCanLogin(Boolean canLogin) {
      if(canLogin != null) {
        this.canLogin = canLogin;
      }
      return this;
    }
  
    public Builder setAdmin(Boolean admin) {
      if(admin != null) {
        this.admin = admin;
      }
      return this;
    }
  
    public Builder setRecievesNotifications(Boolean notifications) {
      if(notifications != null) {
        this.notifications = notifications;
      }
      return this;
    }
    
    public Builder setManagePerformances(Boolean managePerformances) {
      if(managePerformances != null) {
        this.managePerformances = managePerformances;
      }
      return this;
    }
    
    public Builder setManageCasts(Boolean manageCasts) {
      if(manageCasts != null) {
        this.manageCasts = manageCasts;
      }
      return this;
    }
    
    public Builder setManagePieces(Boolean managePieces) {
      if(managePieces != null) {
        this.managePieces = managePieces;
      }
      return this;
    }
    
    public Builder setManageRoles(Boolean manageRoles) {
      if(manageRoles != null) {
        this.manageRoles = manageRoles;
      }
      return this;
    }
    
    public Builder setManageRules(Boolean manageRules) {
      if(manageRules != null) {
        this.manageRules = manageRules;
      }
      return this;
    }

    public User build() throws InvalidParameterException {
      if(firstName == null || lastName == null || email == null) {
        throw new InvalidParameterException(
            "User must have non-null firstName, lastName, and email");
      }
      user.id = this.id;
      user.firstName = this.firstName;
      user.lastName = this.lastName;
      user.email = this.email;
      user.dateJoined = this.dateJoined;
      user.emergencyContactName = this.emergencyContactName;
      user.emergencyContactNumber = this.emergencyContactNumber;
      user.comments = this.comments;
      user.role = this.role;
      user.isActive = this.isActive;
      user.canLogin = this.canLogin;
      user.admin = this.admin;
      user.notifications = this.notifications;
      user.managePerformances = this.managePerformances;
      user.manageCasts = this.manageCasts;
      user.managePieces = this.managePieces;
      user.manageRoles = this.manageRoles;
      user.manageRules = this.manageRules;

      return user;
    }

    public Builder(User user) {
      this.user = user;
      this.id = user.id;
      this.firstName = user.firstName;
      this.lastName = user.lastName;
      this.email = user.email;
      this.dateJoined = user.dateJoined;
      this.emergencyContactName = user.emergencyContactName;
      this.emergencyContactNumber = user.emergencyContactNumber;
      this.comments = user.comments;
      this.role = user.role;
      this.isActive = user.isActive;
      this.canLogin = user.canLogin;
      this.admin = user.admin;
      this.notifications = user.notifications;
      this.managePerformances = user.managePerformances;
      this.manageCasts = user.manageCasts;
      this.managePieces = user.managePieces;
      this.manageRoles = user.manageRoles;
      this.manageRules = user.manageRules;
    }

    public Builder() {
      user = new User();
    }
  }
}
