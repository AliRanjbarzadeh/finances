package ir.aliranjbarzadeh.finances.data.base


interface ResponseObject<out DomainObject : Any?> {
    fun toDomain(): DomainObject
}

