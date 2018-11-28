# Rinecraft Core
The powerhouse of Rinecraft.

### Implemented Features
* Playtime and AFK system to counteract boosting playtime.

### WIP Features
* Rentable land
* Buyable land

### Known Bugs
* Players can rent the same space using a different name.
* Rent for land isn't removed from `RentTimeManager` even if the time has expired.
* Check for if a player already has rented or owned land flags incorrectly resulting
in the message "You already own this land." even though the player doesn't
own the land.