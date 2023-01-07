const sideMenu = document.querySelector("aside");
const menuBtn = document.querySelector("#menu-btn");
const closeBtn = document.querySelector("#close-btn");


menuBtn.addEventListener('click', () => {
    sideMenu.style.display = 'block';
})

closeBtn.addEventListener('click', () => {
    sideMenu.style.display = 'none';
})


function initThemeSelector() {
    const themeToggler = document.querySelector(".theme-toggler");
    const themeStylesheetLink = document.getElementById("themeStylesheetLink");
    const currentTheme = localStorage.getItem("theme") || "default_theme";

    function activateTheme(themeName) {
        themeStylesheetLink.setAttribute("href", `/css/${themeName}.css`);
    }

    themeToggler.addEventListener('click', () => {
        let oldTheme = localStorage.getItem("theme") || "default_theme";
        if (oldTheme === "default_theme") {
            activateTheme("dark_theme");
            localStorage.setItem("theme", "dark_theme");
        }
        else if(oldTheme === "dark_theme"){
            activateTheme("default_theme");
            localStorage.setItem("theme", "default_theme");
        }

        themeToggler.querySelector('span:nth-child(1)').classList.toggle('active');
        themeToggler.querySelector('span:nth-child(2)').classList.toggle('active');
    })

    activateTheme(currentTheme);
}

initThemeSelector();



/////////////////////       Invite A Friend         ////////////////

const inviteFriend = (userId, event) => {
    let url = "/send_friend_request";
    let jsonData = {userId: userId};
    let friendShipDiv =  document.getElementById("friend_id"+userId);
    friendShipDiv.style.color = 'white';
    friendShipDiv.innerText = '...';

    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(jsonData),
        contentType: 'application/json'
    }).done(function (friendShipId){
        console.log('friendship Id : ' + friendShipId);
        friendShipDiv.innerText = 'Sent';
    }).fail(function () {
        alert("fail");
    });
}

/////////////////////       Accept Friend Request         ////////////////
const acceptFriendRequest = (userId, event) => {
    let url = "/accept_friend_request";
    let jsonData = {userId: userId};
    let friendShipDiv = document.getElementById("friend_request_id"+userId);
    friendShipDiv.style.color = 'white';
    friendShipDiv.innerText = '...';

    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(jsonData),
        contentType: 'application/json'
    }).done(function (friendShipId){
        console.log('Accepted friendship Id : ' + friendShipId);
        friendShipDiv.innerText = 'Friends';
    }).fail(function () {
        alert("fail");
    });
}

/////////////////////       Like Post         ////////////////
const likePost = (postId, event) => {
    let url = "/like";
    let jsonData = {postId: postId};
    let likeDiv = document.getElementById("like_id"+postId);
    let totalLikedDiv = document.getElementById("total_liked_id"+postId);
    likeDiv.style.color = "var(--color-danger)";

    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(jsonData),
        contentType: 'application/json'
    }).done(function (likes){
        console.log('Likes' + likes);
        totalLikedDiv.innerText = likes;
    }).fail(function () {
        alert("failed to like");
    });

    likeDiv.onclick = () => {
        unlikePost(postId, event);
    }
}

/////////////////////       UnLike Post         ////////////////

const unlikePost = (postId, event) => {
    let likeDiv = document.getElementById("like_id"+postId);
    let url2 = "/unlike";
    let jsonData = {postId: postId};
    let totalLikedDiv = document.getElementById("total_liked_id"+postId);
    likeDiv.style.color = "var(--color-dark)";

    $.ajax({
        type: "POST",
        url: url2,
        data: JSON.stringify(jsonData),
        contentType: 'application/json'
    }).done(function (likes) {
        console.log('UnLikes ' + likes);
        totalLikedDiv.innerText = likes;
    }).fail(function () {
        alert("failed to unlike")
    });

    likeDiv.onclick = () => {
        likePost(postId, event);
    }
}


/////////////////////       Comment Post         ////////////////

const commentPost = (postId, event, principalFName, principalLName, principalProfile) => {
    let commentInput = document.getElementsByName("insertComment_name"+postId);
    let comment = commentInput[0].value

    let url = "/comment_post";
    let jsonData = {postId: postId, commentText: comment};
    let totalCommentDiv = document.getElementById("total_comment_id"+postId);

    if (comment === null ){
        console.log("Error null");
        return
    }

    if (comment === ""){
        console.log("Error empty");
        return;
    }

    $.ajax({
        type: "POST",
        url: url,
        data: JSON.stringify(jsonData),
        contentType: 'application/json'
    }).done(function (comments) {
        console.log('Comments ' + comments);
        let totalComment = Number(totalCommentDiv.innerText)  + 1;
        totalCommentDiv.innerText = totalComment;
        commentInput[0].value = "";

        let commentsListDiv = document.getElementById("CommentsList_id"+postId);
        commentsListDiv.innerHTML += `
            <div class="update">
                <div class="profile-photo">
                    <img src="${principalProfile}" alt="">
                </div>
                <div class="message">
                    <div class="row"><b style="width: 60%;">${principalFName} ${principalLName}</b> <p style="width: 40%;" ><i>now</i></p></div>
                    <p>${comment}</p>
                </div>
            </div>`;

        console.log(principalFName + " " + principalLName + " " + principalProfile);
    }).fail(function () {
        alert("failed to comment");
    });
}

/////////////////////       List Post's Comment         ////////////////
const listComments = (postId, event) => {
    let commentListDiv = document.getElementById("commentList_id"+postId);
    let commentsDiv = document.getElementById("commentsDiv_id"+postId);
    commentListDiv.style.display = "block";

    commentsDiv.onclick = () => {
        unListComments(postId, event);
    }
}

const unListComments = (postId, event) => {
    let commentListDiv = document.getElementById("commentList_id"+postId);
    let commentsDiv = document.getElementById("commentsDiv_id"+postId);
    commentListDiv.style.display = "none";

    commentsDiv.onclick = () => {
        listComments(postId, event);
    }
}


