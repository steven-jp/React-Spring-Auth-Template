import axios from "axios";

let URL = "http://localhost:8080";

async function createUser(user) {
  axios
    .post(
      URL + "/user/register",
      {
        email: user.email,
        password: user.password,
        confirmedPassword: user.confirmedPassword,
      },
      {
        headers: {
          // // "Content-Type": "application/x-www-form-urlencoded",
          // Authorization:
          //   "Bearer " + window.btoa(user.email + ":" + user.password),
          // "Access-Control-Allow-Origin": "*",
        },
      },
    )
    .then((res) => {
      // if (res.status === 200) {
      //   window.location.assign("/login");
      // }
      console.log(res.data);
    })
    .catch((error) => {
      console.log(error);
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
      {
        headers: {
          Authorization: localStorage.getItem("token"),
        },
      },
    )
    .then((res) => {
      console.log(res);
      let token = res.headers["authorization"];
      console.log(token);
      if (res.status === 200 && token) {
        localStorage.setItem("token", token);
        // window.location.assign("/");
      }
    })
    .catch((error) => {
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

function isLoggedIn() {
  axios
    .get(URL + "/user", {
      headers: {
        Authorization: localStorage.getItem("token"),
      },
    })
    .then((res) => {
      console.log(res);
    })
    .catch((error) => {
      console.log(error);
    });
}

export { createUser, loginUser, logoutUser, isLoggedIn };
