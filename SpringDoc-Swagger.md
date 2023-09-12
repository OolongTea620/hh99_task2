## 

```java
@Entity
public class Team {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @OneToMany(mappedBy = "team", fetch = FetchType.LAZY)
    private List<Member> members = new ArrayList<>();

    public Team() {
    }
}

// Member.java
@Entity
public class Member {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    // 

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn("team_id") 
    private Team team;

    public Member() {
    }
}
```