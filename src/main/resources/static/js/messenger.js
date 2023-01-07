
const messengerBtns = document.querySelectorAll(".messenger-btn");
const closeMessengerBtn = document.querySelector("#close-messenger");
let messengerDisplayed = false;
let messenger = document.querySelector(".messenger");


// Function Show Messenger()
const showMessenger = () => {
    messenger.style.display = "block";
}

// Function Hide Messenger()
const hideMessenger = () => {
    messenger.style.display = "none";
}



///////////////////  Listeners

// clicked Messenger Btn
for(const messengerBtn of messengerBtns){
    messengerBtn.addEventListener('click', () => {
        showMessenger();
    })
}


// closeMessenger
closeMessengerBtn.addEventListener('click', () => {
    hideMessages();
    hideMessenger();
})


/* ===================================           =====================================*/
const title = document.querySelector("#showMessages");
const backMessenger = document.querySelector("#back-messenger");
let form = document.querySelector("#messenger-form");
let footer = document.querySelector("#messenger-footer");
let contacts = document.querySelector("#messenger-contacts");
let messageCounts = document.querySelectorAll(".message-count");

// Function Show Messenger()
const showMessages = () => {
    contacts.style.display = "none";
    form.style.display = "block";
    footer.style.display = "flex";
}

// Function Hide Messenger()
const hideMessages = () => {
    contacts.style.display = "block";
    form.style.display = "none";
    footer.style.display = "none";
    form.innerHTML = ``;
    selectedUser = null;
    messengerHeader.innerText = "Simple online messenger";
}

backMessenger.addEventListener('click', () =>{
    hideMessages();
})

/* ===================================  SocketJs  =====================================*/

const baseUrl = 'http://localhost:8080';
let stompClient;
let selectedUser;
let newMessages = new Map();

let messengerHeader = document.getElementById("messenger-header");

const selectUser = (userFirstName, userLastName, userId, userProfile, event) => {
    console.log("selecting user id : " + userId + " " + userFirstName + " " + userLastName + " " + userProfile);
    selectedUser = userId;
    messengerHeader.innerHTML =
        `<div style="display: flex; gap: 0.5rem; align-items: center; justify-content: start">
            <div class="profile-photo">
                <img src="${userProfile}" alt="profile photo">
            </div>
            <div><span class="danger">@ ${userFirstName} ${userLastName}</span></div>
         </div> `;

    getMyMessages(userId);
    showMessenger()
    showMessages();
    setMessagesStatusSeen(userId);
};

const getMyMessages = (userId) => {
    let principalId = $("#principal-id").val();
    let url = `/get_messages/${principalId}/${userId}`;

    $.ajax({
        type: "GET",
        url: url,
    }).done(function (messages){

        for(const message of messages){
            if (Number(message.sender) === Number(userId)){
                renderIn(message.content);
            }else{
                renderOut(message.content);
            }
        }
    }).fail(function () {
        alert("fail");
    });
}

$(document).ready(() => {
    let principalId = $("#principal-id").val();
    connectToChat(principalId);

    $("#sendMessage").click(() => {
        const message = $("#inputMessage").val();
        sendMessage(message);
        $("#inputMessage").val("");
    })
});

//////////////////////////////////////// Functions

const connectToChat = (principalId) => {
    console.log("I'm connecting to chat with id " + principalId);
    let socket = new SockJS(baseUrl + '/chat');
    stompClient = Stomp.over(socket);
    stompClient.connect({}, (frame) => {
        console.log("Connected Successfully to : " + frame);
        stompClient.subscribe("/topic/messages/" + principalId, (response) => {
            let data = JSON.parse(response.body);
            if (selectedUser === data.fromLogin){
                renderIn(data.message);
                setMessagesStatusSeen(data.fromLogin);
            }
            else{
                newMessages.set(data.fromLogin, data.message);
                updateMessageCount();
            }
        });
    });
};



const sendMsg = (from, text) => {
    stompClient.send("/app/chat/" + selectedUser, {}, JSON.stringify({
        'fromLogin': from,
        'message': text
    }));
};

const sendMessage = (message) => {
    let principalId = $("#principal-id").val();
    console.log(principalId + " is sending");

    if(message.trim() !== ''){
        renderOut(message);
        sendMsg(principalId, message);
    }
};

const renderIn = (message) => {
    let messengerForm = document.getElementById("messenger-form");
    messengerForm.innerHTML += `
        <div class="bot-inbox inbox">
            <div class="icon">
                <span class="material-symbols-sharp">person</span>
            </div>
            <div class="msg-header">
                <p>${message}</p>
                <small>date</small>
            </div>
        </div>
    `;

    $(".messenger-form").scrollTop($(".messenger-form")[0].scrollHeight);
};

const renderOut = (message) => {
    let messengerForm = document.getElementById("messenger-form");
    messengerForm.innerHTML += `
        <div class="user-inbox inbox">
            <div class="msg-header">
                <p>${message}</p>
                <small>date</small>
            </div>
        </div>
    `;

    $("#messenger-form").scrollTop($("#messenger-form")[0].scrollHeight);
};

const setMessagesStatusSeen = (fromLogin) => {
    let principalId = $("#principal-id").val();
    let url = `/set_message_seen/${principalId}/${fromLogin}`;

    $.ajax({
        type: "GET",
        url: url,
    }).done(function (message){

        console.log(message);
    }).fail(function () {
        alert("fail");
    });
}

const updateMessageCount = () => {
    messageCounts.forEach(messageCount => {
        let val = messageCount.textContent;
        let total = 0;

        total =  total + Number(val) + 1;
        messageCount.innerText = total;

    })
}




















