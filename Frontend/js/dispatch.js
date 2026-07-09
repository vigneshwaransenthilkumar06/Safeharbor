checkLogin();

document.getElementById("dispatchForm").addEventListener("submit", createDispatch);

loadDispatches();

async function loadDispatches() {
  try {
    let response = await axios.get(API_URL + "/dispatches", {
      headers: getHeaders(),
    });

    let dispatches = response.data;
    let rows = "";

    dispatches.forEach((dispatch) => {
      rows += `
        <tr>
            <td>${dispatch.id}</td>
            <td>${dispatch.incident ? dispatch.incident.title : ""}</td>
            <td>${dispatch.item ? dispatch.item.itemName : ""}</td>
            <td>${dispatch.quantity}</td>
            <td>${dispatch.status}</td>
            <td>
                <button class="btn btn-sm btn-success" onclick="markDelivered(${dispatch.id})">Mark Delivered</button>
                <button class="btn btn-sm btn-danger" onclick="cancelDispatch(${dispatch.id})">Cancel</button>
            </td>
        </tr>
      `;
    });

    document.getElementById("dispatchTable").innerHTML = rows;
  } catch (error) {
    showError("Unable to load dispatches");
  }
}

async function createDispatch(event) {
  event.preventDefault();

  let dispatch = {
    incidentId: document.getElementById("incidentId").value,
    itemId: document.getElementById("itemId").value,
    quantity: document.getElementById("quantity").value,
  };

  try {
    await axios.post(API_URL + "/dispatches", dispatch, {
      headers: getHeaders(),
    });

    showSuccess("Dispatch Created");
    document.getElementById("dispatchForm").reset();
    loadDispatches();
  } catch (error) {
    showError(error.response ? error.response.data : "Unable to Create Dispatch");
  }
}

async function markDelivered(id) {
  try {
    await axios.patch(
      API_URL + "/dispatches/" + id + "/deliver",
      {},
      { headers: getHeaders() }
    );

    showSuccess("Dispatch Marked as Delivered");
    loadDispatches();
  } catch (error) {
    showError(error.response ? error.response.data : "Unable to Update Dispatch");
  }
}

async function cancelDispatch(id) {
  if (!confirm("Are you sure you want to cancel this dispatch?")) {
    return;
  }

  try {
    await axios.delete(API_URL + "/dispatches/" + id, {
      headers: getHeaders(),
    });

    showSuccess("Dispatch Cancelled");
    loadDispatches();
  } catch (error) {
    showError(error.response ? error.response.data : "Unable to Cancel Dispatch");
  }
}
