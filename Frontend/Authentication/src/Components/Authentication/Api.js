import axios from "axios";

let URL = "http://localhost:3000";

async function createUser(user) {
  axios
    .post(
      URL + "/user/register",
      {
        username: user.username,
        email: user.email,
        password: user.password,
        confirmedPassword: user.confirmedPassword,
      },
      { withCredentials: true, credentials: "include" },
    )
    .then((res) => {
      console.log(res.data);
      if (res.data.id) {
        window.location.assign("/");
      }
    })
    .catch((error) => {
      console.log(error.response.data);
    });
}

async function loginUser(user) {
  axios
    .post(
      URL + "/user/login",
      {
        email: user.email,
        password: user.password,
      },
      { withCredentials: true, credentials: "include" },
    )
    .then((res) => {
      console.log(res.data);
      if (res.data.id) {
        window.location.assign("/");
      }
    })
    .catch((error) => {
      console.log(error);
    });
}

async function logoutUser() {
  axios
    .get(URL + "/user/logout", {
      withCredentials: true,
      credentials: "include",
    })
    .then((res) => {
      console.log(res);
      window.location.assign("/login");
    })
    .catch((error) => {
      console.log(error);
    });
}

function isLoggedIn(setUserData) {
  axios
    .get(URL + "/user/login", { withCredentials: true, credentials: "include" })
    .then((res) => {
      if (res.data) {
        setUserData(res.data);
      }
    })
    .catch((error) => {
      console.log(error);
    });
}

export { createUser, loginUser, logoutUser, isLoggedIn };