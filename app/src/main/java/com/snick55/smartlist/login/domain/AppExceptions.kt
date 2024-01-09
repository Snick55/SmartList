package com.snick55.smartlist.login.domain

import com.snick55.smartlist.core.AppExceptions


class InvalidRequestException: AppExceptions("Некорректный запрос")
class RecaptchaException: AppExceptions("Recaptcha завершилась с ошибкой")
class NoInternetException: AppExceptions("Отсутствует подключение к интернету")
class GenericException: AppExceptions("Что то пошло не так")

class EmptyFieldException(
    val field: Field
) : AppExceptions("Пустое поле")