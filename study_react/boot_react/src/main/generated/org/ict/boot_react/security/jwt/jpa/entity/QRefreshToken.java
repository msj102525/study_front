package org.ict.boot_react.security.jwt.jpa.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QRefreshToken is a Querydsl query type for RefreshToken
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QRefreshToken extends EntityPathBase<RefreshToken> {

    private static final long serialVersionUID = -132003617L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QRefreshToken refreshToken = new QRefreshToken("refreshToken");

    public final DateTimePath<java.time.LocalDateTime> expirationDate = createDateTime("expirationDate", java.time.LocalDateTime.class);

    public final NumberPath<Long> expiresin = createNumber("expiresin", Long.class);

    public final ComparablePath<java.util.UUID> id = createComparable("id", java.util.UUID.class);

    public final DateTimePath<java.time.LocalDateTime> issuedAt = createDateTime("issuedAt", java.time.LocalDateTime.class);

    public final org.ict.boot_react.member.jpa.entity.QMemberEntity memberDto;

    public final StringPath status = createString("status");

    public final StringPath tokenValue = createString("tokenValue");

    public final StringPath userAgent = createString("userAgent");

    public QRefreshToken(String variable) {
        this(RefreshToken.class, forVariable(variable), INITS);
    }

    public QRefreshToken(Path<? extends RefreshToken> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QRefreshToken(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QRefreshToken(PathMetadata metadata, PathInits inits) {
        this(RefreshToken.class, metadata, inits);
    }

    public QRefreshToken(Class<? extends RefreshToken> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.memberDto = inits.isInitialized("memberDto") ? new org.ict.boot_react.member.jpa.entity.QMemberEntity(forProperty("memberDto")) : null;
    }

}

