package acmecollege.entity;

import java.time.LocalDateTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="Dali", date="2023-12-04T13:11:43.748-0500")
@StaticMetamodel(DurationAndStatus.class)
public class DurationAndStatus_ {
	public static volatile SingularAttribute<DurationAndStatus, LocalDateTime> startDate;
	public static volatile SingularAttribute<DurationAndStatus, LocalDateTime> endDate;
	public static volatile SingularAttribute<DurationAndStatus, Byte> active;
}
