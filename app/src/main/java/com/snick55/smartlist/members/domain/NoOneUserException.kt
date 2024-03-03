package com.snick55.smartlist.members.domain

import com.snick55.smartlist.core.AppExceptions

class NoOneUserException: AppExceptions(errorMessage = "Не нашлось ни одного пользователя")