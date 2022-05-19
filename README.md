  # UnluMamullerApp
  Unlu mamül üretim ve satış işletmeleri için geliştirilen bir android tablet uygulaması.

  Uygulamanın admin panelindeki eksik kısımları ve tüm cihazlar için çoklu ekran desteği eklenecektir.
Mevcut ekran destekleri > 8 inch ( admin paneli için çoğu var )

  Uygulamanın çalışma mantığı şu şekildedir;
Uygulama 4 farklı kısımdan oluşuyor Admin-Tezgah-Servis-İmalathane

  Admin kısmında belirlenebilen ve personele verilen kullanıcı giriş bilgileri ile personel, sadece adminin belirlediği kısıma giriş yapabiliyor.

  Imalathane kısmında, üretilen ürünler sisteme giriliyor ve güncel-anlık stok kaydı yapılmış oluyor. Servis kısmına gidicek ve ayrı olması gereken
ürünleri görebiliyor ve ona göre sırasıyla paketlemesini yapabiliyor.

  Servis kısmında servis personeli sisteme kayıtlı olan müşterileri, aldıkları ürünleri sayısıyla görebiliyor. Ürün adetlerini güncelleyebiliyor. 
Para teslim aldıktan sonra satış yapar gibi ödeme alıyor ve alınan ödemeler satılan ürünlerle beraber kaydediliyor. 
Müşterilerin geçmiş borçlarını ve toplam borçlu ürün adetlerini görüntüleyebiliyor.

  Tezgah kısmında tezgah personeli, yaptığı her satışı önce ekranın ortasında bulunan rakamlar ile adet belirtip 
sonrasında ürünün resminin üzerine tıklayarak belirleyebiliyor. Ürünün resminin üzerine tıkladıktan sonra adetiyle beraber sepete 
ekleniyor ve toplam tutarda fiyat gösteriliyor.Satış butonuna bastıktan sonra ise ürünler stoktan düşüyor ve satış tutarıyla birlikte kaydediliyor.
  Veresiye müşteriler var ise müşteriler butonuyla ayrı bir kısımda görüntülenebiliyor. Müşterinin geçmiş borçlarıyla ilgili işlemler oradan yapılabiliyor.
Veresiye müşteriye ürün satılacağı zaman müşteriler kısmına geçip ilgili müşterinin üzerine tıklandığında tezgah ana sayfasındaki toplam tutarın üstüne
müşterinin ismi geliyor. Bu şekilde satış yapıldığında kasaya nakit girişi olmadan müşterinin borcuna toplam tutar ve ürünler ekleniyor.
  Tezgah kısmında olan diğer özellikler şunlardır; Kasadan satın alınan-ödenen şeyler için "Gider" kısmı,
imalathaneden görüntülenebilen "İmalathane Not Gönder" kısmı, anlık stok takibi için "Stok" kısmı, hibe ve askıda ekmek işlemleri için olan 
"Hibe" ve "Askıda Ekmek" kısımları ve son olarak gramaj ile satılan ürünler için tezgahın ana ekranındaki mantığa benzer bir mantıkta çalışan "Gramajlı Satış"
kısımları bulunmaktadır.

Admin panelinde ise tezgah ürünleri, servis ürünleri, personel ve müşteri işlemleri ve bunların düzenlemeleri-görüntülenebilmeleri yapılabilmektedir. 
Günlük, haftalık ve aylık toplam istatistikler özet olarak görüntülenebilmektedir.
Detay istatistik görüntüleme vb. işlemler eklenecektir.

Uygulamada RESTful mimarisiyle yazdığım webservisleri kullanılmıştır. webservisler ayrı bir dosyaya eklenecektir.
