package Shared.Requests;
//import Server.model.*;

import Shared.dto.WaitingListEntryDTO;

import java.io.Serializable;
import java.util.List;


// Response object sent from the server to the client containing all current waiting list entries
// for a specific book. Encapsulates a list of WaitingListEntryDTOs for display in the user interface.
//  Helps decouple server-side models from the client-side display logic.

public class WaitingListResponse implements Serializable {
  private final List<WaitingListEntryDTO> waitingListEntries;

  public WaitingListResponse(List<WaitingListEntryDTO> waitingListEntries) {
    this.waitingListEntries = waitingListEntries;
  }

  public List<WaitingListEntryDTO> getWaitingListEntries() {
    return waitingListEntries;
  }
}
