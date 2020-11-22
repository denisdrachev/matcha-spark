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
      header: 'Authorization',
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
						  "index": 0,
						  "src": "google.com",
						  "avatar": true
					  }
				  ],
				  "time": "Nov 20, 2020, 10:21:14 PM",
				  "location": {
					  "x": 1.3,
					  "y": 2.1,
				  },
				  "isBlocked": false,
				  "isFilled": true,
				  "likedFrom": false,
				  "likedTo": false
			  }
		  }
    },
---------------------------------------------------------------
{
      type: POST,
      path: "/profile-update",
      desc: "User_1: Обновление информации пользователя User_1",
      header: 'Authorization',
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
      path: "/history/:limit/:offset",
      desc: "User_1: Смотрит историю",
      header: 'Authorization',
      request: {
      },
      expect: {
			  "type": "ok",
			  "data": [
				  {
					  "type": "PROFILE_LOAD",
					  "login": "test",
					  "time": "Nov 20, 2020, 10:23:48 PM",
					  "fname": "test",
					  "lname": "test"
				  }
			  ]
		  }
    },

----------------------------------------------------------------

    {
      type: GET,
      path: "/notification/:limit/:offset",
      desc: "User_1: Смотрит уведомления",
      header: 'Authorization',
      request: {
      },
      expect: {
			  "type": "ok",
			  "data": [
				  {
					  "type": "PROFILE_LOAD",
					  "login": "test6",
					  "time": "Nov 20, 2020, 10:23:48 PM",
					  "fname": "test6",
					  "lname": "test6"
				  }
			  ]
		  }
    },

---------------------------------------------------------------
    {
      type: POST,
      path: "/like-user/:login/:value", //1 - like, 0 - dislike
      desc: "User_2: User_2 поставил лайк User_3",
      header: 'Authorization',
      request: {
      },
      expect: {
        type: 'ok',
      },
    },
---------------------------------------------------------------
 {
      type: POST,
      path: "/blacklist/save",
      desc: "Пользователь token добавляет в черный список пользователя test",
      header: 'Authorization',
      request: {
      		toLogin: "test",
      		isBlocked: true
      },
      expect: {
        type: 'ok',
      },
    },
---------------------------------------------------------------
 {
      type: GET,
      path: "/connected",
      desc: "Получить список всех пользователей, которых лайнул Authorization и которые лайкнули его",
      header: 'Authorization',
      request: {
      },
      expect: {
        type: 'ok',
        data: [
				  {
					  "login": "test6",
					  "fname": "test6",
					  "lname": "test6"
				  }
			  ]
      },
},
---------------------------------------------------------------
 {
      type: GET,
      path: "/new-notifications",
      desc: "Получить количествво непрочитанны нотификаций",
      header: 'Authorization',
      request: {
      },
      expect: {
        type: 'ok',
        data: 11
      }
},
---------------------------------------------------------------
 {
      type: POST,
      path: "/fake-user",
      desc: "Сообщить о фейковом пользователе",
      header: 'Authorization',
      request: {
      	login: "User_3"
      },
      expect: {
        type: 'ok'
      }
},
