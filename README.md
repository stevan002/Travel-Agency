Turistička Agencija - Web MVC Aplikacija
Ova aplikacija je namenjena turističkoj agenciji kako bi olakšala upravljanje putovanjima, rezervacijama i interakcijom sa korisnicima. Korisnici aplikacije mogu biti kako registrovani (kupci i menadžeri), tako i neprijavljeni korisnici koji imaju pristup pregledu ponude putovanja.

Funkcionalnosti:

Prijava, Odjava i Registracija
Korisnici se mogu prijaviti na sistem koristeći svoj username i lozinku.
Neprijavljenim korisnicima je omogućen pristup registraciji.
Prilikom registracije, korisnici unose osnovne informacije i kreiraju lozinku.
Lozinka se mora uneti kroz dva polja radi potvrde.

Putovanja
Putovanja su prikazana na početnoj stranici za sve korisnike.
Omogućena je pretraga putovanja po različitim parametrima kao što su prevozno sredstvo, naziv destinacije, kategorija putovanja, broj noćenja i cena aranžmana.
Putovanja se mogu sortirati po nazivu destinacije, ceni i broju noćenja.
Menadžerima je omogućeno dodavanje novih putovanja, ažuriranje podataka o postojećim putovanjima i brisanje putovanja (ukoliko nema rezervacija).
Kupci mogu rezervisati željeno putovanje, pri čemu mogu uneti broj putnika za koje vrše rezervaciju.

Akcije i Pogodnosti
Menadžerima je omogućeno postavljanje akcijskih cena za putovanja.
Akcijske cene imaju određen vremenski period nakon kojeg se vraćaju na regularnu cenu.
Kupci ostvaruju popust samo ako rezervišu putovanje pre isteka akcijskog perioda.

Putovanja na Zahtev
Kupci mogu zatražiti kreiranje individualnog putovanja sa specifičnim zahtevima.
Menadžerima se prikazuju zahtevi za individualna putovanja koje mogu prihvatiti ili odbiti.
Kreirana putovanja su vidljiva samo kupcu koji je zatražio njihovo kreiranje.
Kupci mogu odbiti ili prihvatiti ponuđeno putovanje.

Profil Korisnika
Na profilu korisnika prikazuju se osnovni podaci osim lozinke.
Omogućena je izmena podataka korisnika, uključujući i promenu lozinke.
Kupcima je na profilu prikazana lista rezervacija sa mogućnošću filtriranja po datumu.
Aktivne rezervacije mogu biti otkazane, ali ne manje od 48 sati pre početka putovanja.

Korištene Tehnologije:
Java
Spring Boot
Spring Security
Spring JPA & Hibernate
PostgreSQL (baza podataka)
Git (za verzioniranje koda)
GitHub (za hosting i upravljanje kodom)
