document.addEventListener("DOMContentLoaded", function () {
  let loggedInUser = JSON.parse(sessionStorage.getItem("loggedInUser"));

  if (!loggedInUser) {
      console.error("No registration data found in sessionStorage");
      return;
  }

  // Set profile data from sessionStorage
  document.getElementById("username").innerText = loggedInUser.username || "N/A";
  document.getElementById("phone_number").innerText = loggedInUser.phone_number || "N/A";
  document.getElementById("email").innerText = loggedInUser.email || "N/A";
  document.getElementById("address").innerText = loggedInUser.address || "N/A";

  const editAddressBtn = document.getElementById("editAddressBtn");
  const saveProfileBtn = document.getElementById("saveProfileBtn");
  const addressField = document.getElementById("address");

  // Address editing
  editAddressBtn.addEventListener("click", function () {
      const currentAddress = addressField.innerText;
      addressField.innerHTML = `<input type="text" id="editAddressInput" class="text-black" value="${currentAddress}">`;
      editAddressBtn.classList.add("hidden");
      saveProfileBtn.classList.remove("hidden");
  });

  // Save Profile Data
  saveProfileBtn.addEventListener("click", function () {
      const newAddress = document.getElementById("editAddressInput").value;
      addressField.innerText = newAddress;
      loggedInUser.address = newAddress;
      sessionStorage.setItem("loggedInUser", JSON.stringify(loggedInUser));
      editAddressBtn.classList.remove("hidden");

      saveProfileBtn.classList.add("hidden");
  });
});

function previewImage(event) {
  const reader = new FileReader();
  reader.onload = function(){
      const output = document.getElementById('userImage');
      output.src = reader.result;
  };
  reader.readAsDataURL(event.target.files[0]);
}

