import axios from "axios";

let URL = "http://localhost:8080";

async function createUser(user) {
  axios
    .post(URL + "/user/register", {
      email: user.email,
      password: user.password,
      confirmedPassword: user.confirmedPassword,
    })
    .then((res) => {
      console.log(res.data);
      // if (res.data.id) {
      //   window.location.assign("/");
      // }
    })
    .catch((error) => {
      console.log(error);
    });
}

async function loginUser(user) {
  axios
    .post(
      URL + "/login",
      {
        email: user.email,
        password: user.password,
      },
      {
        headers: {
          // "Content-Type": "application/x-www-form-urlencoded",
          Authorization:
            "Basic " + window.btoa(user.email + ":" + user.password),
          // "Access-Control-Allow-Origin": "*",
        },
      },
      // { withCredentials: true, credentials: "include" },
      // { credentials: "include" },
    )
    .then((res) => {
      console.log(res.data);
      console.log(res);

      // if (res.data.id) {
      //   window.location.assign("/");
      // }
    })
    .catch((error) => {
      // console.log(error);
      console.log(error.response);
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
