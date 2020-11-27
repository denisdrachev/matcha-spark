--------------------------------------
    {
        type: "POST",
        path: "/register",
        desc: "Регистрация User_1",
        request: {
            email: "reinoldskora@gmail.com",
            fname: "maslyn",
            lname: "skiles",
            login: "User_1",
            password: "123",
            location: {
                x: 1.4,
                y: 2.7
            }
        },
        expect: {type: 'ok'}
    }
--------------------------------------
    {
        type: POST,
        path: "/login",
        desc: "Авторизация User_1",
        request: {
            login: 'User_1',
            password: '123',
            location: {y: 37.5420654, x: 55.808093},
        },
        expect: {type: 'ok', token: '11111-22222-3333-4444'}
    }
--------------------------------------
    {
        type: GET,
        path: "/profile-get/:login",
        desc: "User_2: Запрос пользователя по токену",
        header: 'Authorization',
        request: {},
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
    }
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
            location: {y: 37.5420654, x: 55.808093},
            email: "reinoldskora@gmail.com",
            password: "12345",
            preference: [0, 1],
            tags: ["poker"],
        },
        expect: {
            type: 'ok'
        }
    }
----------------------------------------------------------------
    {
        type: GET,
        path: "/history/:limit/:offset",
        desc: "User_1: Смотрит историю",
        header: 'Authorization',
        request: {},
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
    }
----------------------------------------------------------------
    {
        type: GET,
        path: "/notification/:limit/:offset",
        desc: "User_1: Смотрит уведомления",
        header: 'Authorization',
        request: {},
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
    }
---------------------------------------------------------------
    {
        type: POST,
        path: "/like-user", //1 - like, 0 - dislike
        desc: "User_2: User_2 поставил лайк User_3",
        header: 'Authorization',
        request: {
            login: "User_3",
            value: 1
        },
        expect: {
            type: 'ok',
        }
    }
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
        }
    }
---------------------------------------------------------------
    {
        type: GET,
        path: "/connected",
        desc: "Получить список всех пользователей, которых лайнул Authorization и которые лайкнули его",
        header: 'Authorization',
        request: {},
        expect: {
            type: 'ok',
            data: [
                {
                    "login": "test6",
                    "fname": "test6",
                    "lname": "test6"
                }
            ]
        }
    }
---------------------------------------------------------------
    {
        type: GET,
        path: "/new-notifications",
        desc: "Получить количествво непрочитанны нотификаций",
        header: 'Authorization',
        request: {},
        expect: {
            type: 'ok',
            data: 11
        }
    }
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
    }
-------------------------------------------------------------------
    //Все параметры в запросе являются необязательными
    //Допустимые значения:
    // ageMin => 0, ageMin <= 150
    // ageMax => 0, ageMax <= 150
    // minRating >= 0
    // maxRating >= 0
    // deltaRadius >= 0
    // limit > 0, limit <= 100
    // offset >= 0
    //sortAge, sortLocation, sortRating, sortTags [-1: DESC, 1: ASC]
    {
        type: GET,
        path: "/get-users?tags=tag2,tag4&ageMin=0&ageMax=100&minRating=0&maxRating=999&deltaRadius=1000&limit=10&offset=0&sortAge=1&sortLocation=1&sortRating-1&sortTags=1",
        desc: "User_2: Поиск пользователей с использованием фильтров",
        header: 'Authorization',
        request: {},
        expect: {
            "type": "ok",
            "data": [
                {
                    login = "User_2",
                    fname = "fname",
                    lname = "lname",
                    x = 1.1,
                    y = 1.1,
                    rating = 3,
                    tagsCount = 1,
                    src = "https://i.ibb.co/cw9TnX9/0f6339673fed.jpg"
                },
                {
                    login = "test3",
                    fname = "first name",
                    lname = "last name",
                    x = 1.1,
                    y = 1.1,
                    rating = 7,
                    tagsCount = 2,
                    src = "https://i.ibb.co/cw9TnX9/0f6339673fed.jpg"
                }
            ]
        }
    }
---------------------------------------------------------------------------
    {
        type: POST,
        path: "/password-reset",
        desc: "Авторизованный пользователь кликает на смену пароля и ему на почту отправляется письмо с ключом",
        header: 'Authorization',
        request: {},
        expect: {
            type: 'ok'
        }
    }
---------------------------------------------------------------------------
    {
        type: POST,
        path: "/change-reset-password",
        desc: "Пользователь обновляет пароль своего аккаунта",
        header: 'Authorization',
        request: {
            "password": "new_password"
        },
        expect: {
            type: 'ok'
        }
    }
---------------------------------------------------------------------------
    {
        type: POST,
        path: "/chat/save",
        desc: "Пользователь отправляет сообщение пользователю",
        header: 'Authorization',
        request: {
            "message": "текст сообщения",
            "toLogin": "логин пользователя, которому отправлено сообщение"
        },
        expect: {
            type: 'ok'
        }
    }
---------------------------------------------------------------------------
    {
        type: GET,
        path: "/chat/full",
        desc: "Получает последние limit сообщений между пользователями toLogin и fromLogin. Если среди них есть непрочитанные - обновляет их в базу как прочитанные. Возвращает сообщения",
        header: 'Authorization',
        request: {
            "fromLogin": "User_2",
            "toLogin": "test2",
            "limit": "максимальное количество сообщений" //больше 0
        },
        expect: {
            type: 'ok',
            "data": [{
                "toLogin": "test2",
                "fromLogin": "User_2",
                "message": "dasd 324 343",
                "time": "Nov 26, 2020, 2:07:16 AM",
                "read": false
            }, {
                "toLogin": "test2",
                "fromLogin": "User_2",
                "message": "dasd 324 343",
                "time": "Nov 26, 2020, 2:07:15 AM",
                "read": false
            }, {
                "toLogin": "test2",
                "fromLogin": "User_2",
                "message": "dasd 324 343",
                "time": "Nov 26, 2020, 2:07:14 AM",
                "read": false
            }]
        }
    }
---------------------------------------------------------------------------
    /**
     * 1. Ищет все непрочитанные сообщения от toLogin к fromLogin
     * 2. Смотрит флаг во входящем запросе. Помечать ли полученные сообщения как прочитанные? Если да - помечает
     * 3. Возвращает их
     */
    {
        type: GET,
        path: "/chat/new",
        desc: "Получает непрочитанные сообщения от собеседника",
        header: 'Authorization',
        request: {
            "fromLogin": "Login1",
            "toLogin": "login2"
        },
        expect: {
            type: 'ok',
            "data": [{
                "toLogin": "login2",
                "fromLogin": "Login1",
                "message": "dasd 324 343",
                "time": "Nov 26, 2020, 2:07:16 AM",
                "read": false
            }, {
                "toLogin": "login2",
                "fromLogin": "Login1",
                "message": "dasd 324 343",
                "time": "Nov 26, 2020, 2:07:15 AM",
                "read": false
            }, {
                "toLogin": "login2",
                "fromLogin": "Login1",
                "message": "dasd 324 343",
                "time": "Nov 26, 2020, 2:07:14 AM",
                "read": false
            }]
        }
    }