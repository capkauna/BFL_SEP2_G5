package Shared.Requests;
//import Server.model.*;

import Shared.dto.WaitingListEntryDTO;

import java.io.Serializable;
import java.util.List;

public class WaitingListResponse implements Serializable {
  private final List<WaitingListEntryDTO> waitingListEntries;

  public WaitingListResponse(List<WaitingListEntryDTO> waitingListEntries) {
    this.waitingListEntries = waitingListEntries;
  }

  public List<WaitingListEntryDTO> getWaitingListEntries() {
    return waitingListEntries;
  }
}
