checkLogin();

document.getElementById("inventoryForm").addEventListener("submit", addItem);

loadInventory();

async function loadInventory() {
  try {
    let response = await axios.get(API_URL + "/inventory", {
      headers: getHeaders(),
    });
    renderTable(response.data);
  } catch (error) {
    showError("Unable to load inventory");
  }
}

async function loadLowStock() {
  try {
    let response = await axios.get(API_URL + "/inventory/low-stock", {
      headers: getHeaders(),
    });
    renderTable(response.data);
  } catch (error) {
    showError("Unable to load low stock items");
  }
}

function renderTable(items) {
  let rows = "";

  items.forEach((item) => {
    rows += `
        <tr>
            <td>${item.id}</td>
            <td>${item.itemName}</td>
            <td>${item.category || ""}</td>
            <td>${item.quantity}</td>
            <td>${item.unit || ""}</td>
            <td>${item.criticalThreshold}</td>
            <td>
                <button class="btn btn-sm btn-danger" onclick="deleteItem(${item.id})">Delete</button>
            </td>
        </tr>
      `;
  });

  document.getElementById("inventoryTable").innerHTML = rows;
}

async function addItem(event) {
  event.preventDefault();

  let item = {
    itemName: document.getElementById("itemName").value.trim(),
    category: document.getElementById("category").value,
    quantity: document.getElementById("quantity").value,
    unit: document.getElementById("unit").value.trim(),
    criticalThreshold: document.getElementById("criticalThreshold").value || 20,
  };

  if (item.itemName == "") {
    showError("Item Name is required");
    return;
  }

  try {
    await axios.post(API_URL + "/inventory", item, {
      headers: getHeaders(),
    });

    showSuccess("Item Added");
    document.getElementById("inventoryForm").reset();
    loadInventory();
  } catch (error) {
    showError(error.response ? error.response.data : "Unable to Add Item");
  }
}

async function deleteItem(id) {
  if (!confirm("Are you sure you want to delete this item?")) {
    return;
  }

  try {
    await axios.delete(API_URL + "/inventory/" + id, {
      headers: getHeaders(),
    });

    showSuccess("Item Deleted");
    loadInventory();
  } catch (error) {
    showError(error.response ? error.response.data : "Unable to Delete Item");
  }
}
