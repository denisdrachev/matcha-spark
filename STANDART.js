    {
      type: POST,
      path: "/register",
      desc: "Регистрация User_1",
      request: {
        email: "reinoldskora@gmail.com",
        fname: "maslyn",
        lname: "skiles",
        login: "User_1",
        password: "123",
        location: {
        		x:1.4,
        		y:2.7
        	}
      },
      expect: { type: 'ok' },
    }
--------------------------------------
{
      type: POST,
      path: "/login",
      desc: "Авторизация User_1",
      request: {
        login: 'User_1',
        password: '123',
        location: { y: 37.5420654, x: 55.808093 },
      },
      expect: { type: 'ok', token: '11111-22222-3333-4444' }
    },
--------------------------------------
{
      type: GET,
      path: "/profile-get/:login",
      desc: "User_2: Запрос пользователя по токену",
      cookie: 'token',
      request: {
      },
      expect: {
            "type": "ok",
            "data": {
                "login": "test2",
                "fname": "test",
                "lname": "test",
                "age": 111,
                "gender": 0,
                "preference": [
                    0,
                    1
                ],
                "biography": "dasdasdasd",
                "tags": [
                    "dasdas",
                    "dasxzcvxvc"
                ],
                "images": [
                    {
                        "id": 1,
                        "index": 0,
                        "src": "google.com",
                        "profileId": 1,
                        "avatar": true
                    },
                    {
                        "id": 2,
                        "index": 1,
                        "profileId": 1,
                        "avatar": false
                    },
                    {
                        "id": 3,
                        "index": 2,
                        "profileId": 1,
                        "avatar": false
                    },
                    {
                        "id": 4,
                        "index": 3,
                        "profileId": 1,
                        "avatar": false
                    },
                    {
                        "id": 5,
                        "index": 4,
                        "profileId": 1,
                        "avatar": false
                    }
                ],
                "time": "Nov 19, 2020, 11:21:28 PM",
                "location": {
                    "id": 5,
                    "profileId": 1,
                    "x": 1.3,
                    "y": 2.1,
                    "active": true,
                    "time": "Nov 19, 2020, 11:21:28 PM"
                },
                "isBlocked": false,
                "isFilled": true,
                "likedFrom": false,
                "likedTo": false
            }
        },
    },
---------------------------------------------------------------
{
      type: POST,
      path: "/profile-update",
      desc: "User_1: Обновление информации пользователя User_1",
      cookie: 'token',
      request: {
        login: "test",
        age: 22,
        biography: "123",
        fname: "User_1",
        gender: "male",
        images: [
            {
              src: "https://i.ibb.co/sgwqj1R/f824d53e3ffa.png",
              index: 0,
              avatar: true
            }
        ],
        lname: "skiles",
        location: { y: 37.5420654, x: 55.808093 },
        email: "reinoldskora@gmail.com",
        password: "12345",
        preference: [0, 1],
        tags: ["poker"],
      },
      expect: {
        type: 'ok'
      },
    },
----------------------------------------------------------------
    {
      type: GET,
      path: "/history",
      desc: "User_1: Смотрит историю",
      cookie: 'token',
      request: {
      },
      expect: {
        type: 'ok',
        data: [
          {
            action: 'visit',
            target: users.User_2,
            // created: ...
          },
        ]
      }
    },

----------------------------------------------------------------

    {
      type: GET,
      path: "/notifications",
      desc: "User_1: Смотрит уведомления",
      cookie: 'token',
      request: {
      },
      expect: {
        type: 'ok',
        data: [
          {
            action: 'visit',
            target: users.User_2,
            // created: ...
          },
        ]
      }
    },
