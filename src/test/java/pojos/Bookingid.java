package pojos;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Builder

public class Bookingid {

    public int bookingid;
    private BookingData booking;
}
