package Requests;
dto.WaitingListEntryDTO;
model.WaitingList;

import dto.WaitingListEntryDTO;

import java.io.Serializable;
import java.util.List;
import java.util.stream.Collectors;

public class WaitingListResponse implements Serializable {
  private final List<WaitingListEntryDTO> waitingListEntries;

  public WaitingListResponse(List<WaitingList> waitingLists) {
    this.waitingListEntries = waitingLists.stream()
        .map(wl -> new WaitingListEntryDTO(
            wl.getUser().getUserName(),
            wl.getRequestDate()
        ))
        .collect(Collectors.toList());
  }

  public List<WaitingListEntryDTO> getWaitingListEntries() {
    return waitingListEntries;
  }
}
