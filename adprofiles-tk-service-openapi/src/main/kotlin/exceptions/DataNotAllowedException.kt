package services.exceptions

import ru.tk.adprofiles.openapi.models.BaseMessage


class DataNotAllowedException(msg: String, request: BaseMessage) : Throwable("$msg: $request")
