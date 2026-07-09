checkLogin();

document.getElementById("incidentForm").addEventListener("submit", reportIncident);

loadIncidents();

async function loadIncidents() {
  try {
    let response = await axios.get(API_URL + "/incidents", {
      headers: getHeaders(),
    });

    let incidents = response.data;
    let rows = "";

    incidents.forEach((incident) => {
      rows += `
        <tr>
            <td>${incident.id}</td>
            <td>${incident.title}</td>
            <td>${incident.incidentType || ""}</td>
            <td>${incident.severityLevel || ""}</td>
            <td>${incident.location || ""}</td>
            <td>${incident.status}</td>
            <td>${incident.assignedResponder || "-"}</td>
            <td>
                <button class="btn btn-sm btn-warning" onclick="assignResponder(${incident.id})">Assign</button>
                <button class="btn btn-sm btn-success" onclick="resolveIncident(${incident.id})">Resolve</button>
                <button class="btn btn-sm btn-danger" onclick="deleteIncident(${incident.id})">Delete</button>
            </td>
        </tr>
      `;
    });

    document.getElementById("incidentTable").innerHTML = rows;
  } catch (error) {
    showError("Unable to load incidents");
  }
}

async function reportIncident(event) {
  event.preventDefault();

  let incident = {
    title: document.getElementById("title").value.trim(),
    incidentType: document.getElementById("incidentType").value,
    severityLevel: document.getElementById("severityLevel").value,
    location: document.getElementById("location").value.trim(),
    description: document.getElementById("description").value.trim(),
  };

  if (incident.title == "") {
    showError("Title is required");
    return;
  }

  try {
    await axios.post(API_URL + "/incidents", incident, {
      headers: getHeaders(),
    });

    showSuccess("Incident Reported");
    document.getElementById("incidentForm").reset();
    loadIncidents();
  } catch (error) {
    showError(error.response ? error.response.data : "Unable to Report Incident");
  }
}

async function assignResponder(id) {
    let responderName = prompt("Enter responder name:");

    if (!responderName) return;

    try {
        await axios.patch(
            API_URL + "/incidents/" + id + "/assign?responderName=" + encodeURIComponent(responderName),
            {},
            { headers: getHeaders() }
        );

        showSuccess("Responder Assigned");
        loadIncidents();

    } catch (error) {
        showError(error.response ? error.response.data : "Unable to Assign Responder");
    }
}
async function resolveIncident(id) {
  try {
    await axios.patch(
      API_URL + "/incidents/" + id + "/status?status=RESOLVED",
      {},
      { headers: getHeaders() }
    );

    showSuccess("Incident marked as Resolved");
    loadIncidents();
  } catch (error) {
    showError(error.response ? error.response.data : "Unable to Update Status");
  }
}

async function deleteIncident(id) {
  if (!confirm("Are you sure you want to delete this incident?")) {
    return;
  }

  try {
    await axios.delete(API_URL + "/incidents/" + id, {
      headers: getHeaders(),
    });

    showSuccess("Incident Deleted");
    loadIncidents();
  } catch (error) {
    showError(error.response ? error.response.data : "Unable to Delete Incident");
  }
}
