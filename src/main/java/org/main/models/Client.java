package org.main.models;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import java.time.OffsetDateTime;
import java.util.Set;
import java.util.UUID;

@Entity
@Table(name = "clients")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Client {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "client_id", nullable = false, unique = true, length = 255)
    private String clientId;

    @Column(name = "client_name", nullable = false)
    private String clientName;

    @Column(name = "client_secret")
    private String clientSecret;

    @Column(name = "client_type", nullable = false, length = 50)
    private String clientType;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "redirect_uris", columnDefinition = "jsonb", nullable = false)
    private Set<String> redirectUris;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "allowed_grant_types", columnDefinition = "jsonb", nullable = false)
    private Set<String> allowedGrantTypes;

    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "allowed_scopes", columnDefinition = "jsonb", nullable = false)
    private Set<String> allowedScopes;

    @Builder.Default
    @Column(name = "access_token_ttl_seconds", nullable = false)
    private Long accessTokenTtlSeconds = 900L;

    // Configurable Refresh Token TTL in seconds (e.g., 2592000 = 30 days)
    @Builder.Default
    @Column(name = "refresh_token_ttl_seconds", nullable = false)
    private Long refreshTokenTtlSeconds = 2592000L;

    // Security constraint: Require PKCE even for confidential clients
    @Builder.Default
    @Column(name = "require_pkce", nullable = false)
    private boolean requirePkce = true;

    @CreationTimestamp
    @Column(name = "created_at", nullable = false, updatable = false)
    private OffsetDateTime createdAt;
}
