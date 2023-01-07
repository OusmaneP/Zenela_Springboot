import InstantSearch from "./InstantSearch.js";

const searchUsers = document.querySelector("#searchUsers");
const instantSearchUsers = new InstantSearch(searchUsers, {
    searchUrl: new URL("/search", window.location.origin),
    queryParam: "q",
    responseParser: (responseData) => {
        return responseData.results;
    },
    templateFunction: (result) => {
        return `
            <div class="is_line">
                <div class="profile-photo"><img src="${result.profile}" class="is_profile"> </div>
                <div class="is_title">${result.firstName} ${result.lastName}</div>
            </div>
        `;
    }
});

console.log(instantSearchUsers);

// Is Mobile Show
const isMobile = document.querySelector(".is_mobile");
const closeIs = document.querySelector(".close_search");

isMobile.addEventListener("click", () => {
    showSearchUsers();
})

const showSearchUsers = () => {
    searchUsers.style.display = "block";
    isMobile.style.display = "none";
}

// Is Mobile Hide
closeIs.addEventListener("click", () => {
    hideSearchUsers();
})

const hideSearchUsers = () => {
    searchUsers.style.display = "none";
    isMobile.style.display = "block";
}


































