checkLogin();

document.getElementById("shelterForm").addEventListener("submit", registerShelter);

loadShelters();

async function loadShelters() {
  try {
    let response = await axios.get(API_URL + "/shelters", {
      headers: getHeaders(),
    });

    let shelters = response.data;
    let rows = "";

    shelters.forEach((shelter) => {
      rows += `
        <tr>
            <td>${shelter.id}</td>
            <td>${shelter.shelterName}</td>
            <td>${shelter.address || ""}</td>
            <td>${shelter.capacity}</td>
            <td>${shelter.currentOccupancy}</td>
            <td>${shelter.managerName || ""}</td>
            <td>
                <button class="btn btn-sm btn-success" onclick="addPeople(${shelter.id})">Add People</button>
                <button class="btn btn-sm btn-warning" onclick="removePeople(${shelter.id})">Remove People</button>
                <button class="btn btn-sm btn-danger" onclick="deleteShelter(${shelter.id})">Delete</button>
            </td>
        </tr>
      `;
    });

    document.getElementById("shelterTable").innerHTML = rows;
  } catch (error) {
    showError("Unable to load shelters");
  }
}

async function registerShelter(event) {
  event.preventDefault();

  let shelter = {
    shelterName: document.getElementById("shelterName").value.trim(),
    address: document.getElementById("address").value.trim(),
    capacity: document.getElementById("capacity").value,
    managerName: document.getElementById("managerName").value.trim(),
  };

  if (shelter.shelterName == "") {
    showError("Shelter Name is required");
    return;
  }
  if (shelter.capacity == "") {
    showError("Capacity is required");
    return;
  }

  try {
    await axios.post(API_URL + "/shelters", shelter, {
      headers: getHeaders(),
    });

    showSuccess("Shelter Registered");
    document.getElementById("shelterForm").reset();
    loadShelters();
  } catch (error) {
    showError(error.response ? error.response.data : "Unable to Register Shelter");
  }
}

async function addPeople(id) {
  let count = prompt("How many people to add?");
  if (!count) {
    return;
  }
  await changeOccupancy(id, Number(count));
}

async function removePeople(id) {
  let count = prompt("How many people to remove?");
  if (!count) {
    return;
  }
  await changeOccupancy(id, -Number(count));
}

async function changeOccupancy(id, change) {
  try {
    await axios.patch(
      API_URL + "/shelters/" + id + "/occupancy?change=" + change,
      {},
      { headers: getHeaders() }
    );

    showSuccess("Occupancy Updated");
    loadShelters();
  } catch (error) {
    showError(error.response ? error.response.data : "Unable to Update Occupancy");
  }
}

async function deleteShelter(id) {
  if (!confirm("Are you sure you want to delete this shelter?")) {
    return;
  }

  try {
    await axios.delete(API_URL + "/shelters/" + id, {
      headers: getHeaders(),
    });

    showSuccess("Shelter Deleted");
    loadShelters();
  } catch (error) {
    showError(error.response ? error.response.data : "Unable to Delete Shelter");
  }
}
