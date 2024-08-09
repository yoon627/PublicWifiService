# PublicWifiService
위치에 기반해 20개의 서울시 공공와이파이를 알려주는 서비스입니다.

서비스에 필요한 정보들을 관리하기 위한 dto로 와이파이 정보를 담을 Wifi, 북마크 그룹의 정보를 담을 Bookmark, 
북마크된 와이파이 정보를 담을 BookmarkWifi, 현재 위치를 불러올 때마다 기록을 남겨둘 LocationHistory을 java 파일로 생성해 주었습니다.

PublicWifiService.java을 통해 서비스내 필요한 로직들(와이파이 저장, 북마크 관리, 등)을 제공합니다.
