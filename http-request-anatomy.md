# HTTP İsteğinin Anatomisi

HTTP (HyperText Transfer Protocol), istemci ile sunucu arasındaki iletişimi düzenleyen uygulama katmanı protokolüdür. Her HTTP isteği üç temel parçadan oluşur:

```
┌─────────────────────────────────┐
│  1. Request Line                │
├─────────────────────────────────┤
│  2. Headers                     │
├─────────────────────────────────┤
│  3. Body (opsiyonel)            │
└─────────────────────────────────┘
```

---

## 1. Request Line

İsteğin ilk satırıdır. Üç bilgi içerir: **metot**, **yol** ve **HTTP versiyonu**.

```
POST /api/users HTTP/1.1
```

### HTTP Metotları

| Metot    | Amaç                      | Body Var mı? |
|----------|----------------------------|--------------|
| `GET`    | Veri okuma                 | Hayır        |
| `POST`   | Yeni kaynak oluşturma      | Evet         |
| `PUT`    | Kaynağı tamamen güncelleme | Evet         |
| `PATCH`  | Kaynağı kısmen güncelleme  | Evet         |
| `DELETE` | Kaynak silme               | Nadiren      |
| `HEAD`   | GET gibi ama sadece header döner | Hayır  |
| `OPTIONS`| Sunucunun desteklediği metotları sorgulama | Hayır |

### URL Yapısı

```
https://api.example.com:8443/v2/users?role=admin&active=true
└─┬──┘ └──────┬───────┘└┬─┘└──┬───┘└───────────┬──────────┘
scheme       host      port  path          query string
```

- **Scheme:** Protokol (`http` veya `https`)
- **Host:** Sunucu adresi
- **Port:** Bağlantı portu (varsayılan HTTP=80, HTTPS=443)
- **Path:** Kaynağın yolu
- **Query String:** `?` ile başlayan filtre/parametre çiftleri

---

## 2. Headers

İstek hakkında meta bilgi taşıyan `Anahtar: Değer` çiftleridir. Header'lardan sonra boş bir satır bırakılarak body'den ayrılır.

```http
Host: api.example.com
Content-Type: application/json
Authorization: Bearer eyJhbGciOi...
Accept: application/json
User-Agent: Mozilla/5.0
```

### Sık Kullanılan Header'lar

| Header          | Ne İşe Yarar                             | Örnek Değer                    |
|-----------------|------------------------------------------|--------------------------------|
| `Host`          | Hedef sunucu adresi (HTTP/1.1'de zorunlu)| `api.example.com`              |
| `Content-Type`  | Body'nin veri formatı                    | `application/json`             |
| `Authorization` | Kimlik doğrulama bilgisi                 | `Bearer <token>`, `Basic <b64>`|
| `Accept`        | İstemcinin beklediği yanıt formatı       | `application/json`             |
| `User-Agent`    | İstemci yazılım bilgisi                  | `Mozilla/5.0 ...`              |
| `Cookie`        | Sunucuya gönderilen çerezler             | `session_id=abc123`            |
| `Content-Length` | Body'nin byte cinsinden boyutu          | `256`                          |
| `Accept-Encoding`| İstemcinin desteklediği sıkıştırma      | `gzip, deflate, br`           |

---

## 3. Body

Sunucuya gönderilecek veriyi taşır. `GET` ve `DELETE` isteklerinde genellikle body olmaz.

### JSON (en yaygın)

```http
POST /api/users HTTP/1.1
Host: api.example.com
Content-Type: application/json

{
  "name": "ahmet",
  "email": "ahmet@example.com",
  "role": "engineer"
}
```

---

## Tam Örnek

```http
POST /api/orders HTTP/1.1         
Host: shop.example.com                  
Content-Type: application/json
Authorization: Bearer TOKEN123
Accept: application/json
Content-Length: 47
 
{
  "product_id": 101,
  "quantity": 2
}
```

---

## İsteğin Yaşam Döngüsü

```
İstemci                                     Sunucu
   │                                           │
   │  1. DNS Çözümleme (domain → IP)           │
   │─────────────────────────────────────────→ │
   │  2. TCP Bağlantısı (3-way handshake)      │
   │◄────────────────────────────────────────→ │
   │  3. TLS Handshake (HTTPS ise)             │
   │◄────────────────────────────────────────→ │
   │  4. HTTP İsteği Gönderilir                │
   │─────────────────────────────────────────→ │
   │  5. Sunucu işler (routing, auth, DB...)   │
   │                                           │
   │  6. HTTP Yanıtı Döner                     │
   │◄───────────────────────────────────────── │
```

---

## Temel HTTP Durum Kodları

| Kod | Anlamı                    | Açıklama                              |
|-----|---------------------------|---------------------------------------|
| 200 | OK                        | İstek başarılı                        |
| 201 | Created                   | Yeni kaynak oluşturuldu               |
| 204 | No Content                | Başarılı ama body yok                 |
| 301 | Moved Permanently         | Kalıcı yönlendirme                    |
| 400 | Bad Request               | Geçersiz istek (validasyon hatası)    |
| 401 | Unauthorized              | Kimlik doğrulama gerekli              |
| 403 | Forbidden                 | Yetki yok                             |
| 404 | Not Found                 | Kaynak bulunamadı                     |
| 429 | Too Many Requests         | Rate limit aşıldı                     |
| 500 | Internal Server Error     | Sunucu taraflı hata                   |
| 502 | Bad Gateway               | Arka plan servisi yanıt vermedi       |
| 503 | Service Unavailable       | Sunucu geçici olarak hizmet dışı      |
