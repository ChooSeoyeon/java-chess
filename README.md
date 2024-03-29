# java-chess

체스 미션 저장소

## 우아한테크코스 코드리뷰

- [온라인 코드 리뷰 과정](https://github.com/woowacourse/woowacourse-docs/blob/master/maincourse/README.md)

# 체스 게임 설명

- [대한체스연맹](http://www.kchess.or.kr/%EB%8C%80%EA%B5%AD%EB%B0%A9%EB%B2%95/)의 대국방법을 차용했습니다.

## 기본 규칙

- 두 사람이 서로 번갈아가면서 체스판 위에서 기물들을 움직이며 둡니다. ✅
- 백색 기물을 잡은 쪽이 항상 먼저 시작합니다. ✅
- 상대편한테 킹을 잡히면 패배하고 게임이 종료됩니다.

## 행마법

- 비숍(Bishop)
    - 비숍은 대각선으로 원하는 만큼 움직입니다. ✅
- 룩(Rook)
    - 룩은 직선으로 원하는 만큼 움직입니다. ✅
- 퀸(Queen)
    - 퀸은 직선과 대각선으로 원하는 만큼 움직입니다. ✅
- 나이트(Knight)
    - 나이트는 옆으로 한 칸, 앞으로 두 칸 움직입니다. 또는 옆으로 두 칸, 앞으로 한 칸 움직인다고 말할 수 있습니다. ✅
    - 나이트는 전후좌우 모든 방향으로 이와 같이 움직일 수 있습니다. ✅
    - 나이트는 다른 기물들을 뛰어넘을 수 있습니다. ✅
- 폰(Pawn)
    - 폰은 시작 포지션에 있을 경우에는 두 칸 직진할 수 있으며, 앞 방향으로 한 칸씩 직진합니다. ✅
    - 폰은 옆이나 뒤로는 움직일 수 없습니다. ✅
    - 폰의 바로 앞 칸에 기물이 있을 경우에는 그 폰은 움직일 수 없습니다. ✅
    - 폰은 앞 방향 대각선 방향에 상대 기물이 있을 경우, 그 기물을 잡을 수 있습니다. ✅
- 킹(King)
    - 킹은 전후좌우 대각선 어느 방향이로든 한 칸 움직일 수 있습니다. ✅

## 점수 계산 규칙

- 비숍(Bishop): 3점 ✅
- 룩(Rook): 5점 ✅
- 퀸(Queen): 9점 ✅
- 나이트(Knight): 2.5점 ✅
- 폰(Pawn): 1점 ✅
    - 세로줄(File)에 같은 색의 폰이 있는 경우 1점이 아닌 0.5점을 줍니다.
- 킹(King): 0점 ✅

## 용어

- 열(File) -> 함수 그래프에서 x를 의미
    - 열은 체스보드에서 세로 줄을 의미합니다. 8개의 열은 백을 기준으로 왼쪽부터 알파벳(a~h)을 부여받습니다. 아래 그림에서 색칠되어 있는 열은 e 열입니다.
    - <img src="./image/file.png" height="300" width="300">
- 행(Rank) -> 함수 그래프에서 y를 의미
    - 행은 체스보드에서 가로 줄을 의미합니다. 8개의 행은 백을 기준으로 아래쪽부터 숫자(1~8)를 부여받습니다. 아래 그림에서 색칠되어 있는 행은 4번째 행입니다.
    - <img src="./image/rank.png" height="300" width="300">
- 슬라이딩 기물(Sliding Piece) -> 경로에 다른 기물이 있으면 움직이지 못하는 기물 [Sliding Piece](https://www.chessprogramming.org/Sliding_Pieces)
- 점핑 기물(Jumping Piece) -> 경로에 다른 기물이 있어도 움직일 수 있는 기물

# 구현 기능 목록

## 비즈니스 기능

1. [게임 시작] 게임을 시작하면 보드를 초기화한다 ✅
    - 보드는 64개의 칸으로 구성한다 ✅
    - 각 기물을 시작 위치에 초기화한다 ✅
2. [말 이동] 기물이 특정 위치로 움직일 수 있으면 움직인다 ✅
    - 기물의 종류에 따라 움직일 수 있는지 확인한다 ✅
    - 다른 기물의 위치에 따라 움직일 수 있는지 확인한다 ✅
    - 현재 턴에 맞는 색깔의 기물인지 확인힌다 ✅
3. [상태 확인] 각 진영의 점수를 계산하고 승패를 결정한다
    - 각 진영의 점수는 기물의 점수 합이다 ✅
    - 점수가 더 높은 진영이 승리한다
4. 킹이 잡히면 상태를 확인하고 게임을 종료한다
5. [게임 종료] 종료 명령을 받으면 곧바로 게임을 종료한다 ✅

![initialBoard](./image/initialBoard.png)

## 입출력 기능

### 입력

1. `게임 시작` 명령을 입력받는다 ✅
    - start
2. `말 이동` 혹은 `상태 확인` 혹은 `게임 종료` 명령을 입력받는다
    - move b2, b3
    - status
    - end

### 출력

1. 게임 시작 안내 문구를 출력한다 ✅
2. 체스판을 출력한다 ✅
3. 각 진영의 점수와 승패를 출력한다

# 부록

## 고민한 부분

- 체스판의 좌표를 어디까지 추상화(객체로 분리)할 것인가?
    - a1을 물론 a 와 1을 각각 추상화해 객체로 다룬다면, 도메인단에서 체스판의 위치까지 더 명확하게 드러낼 수 있지만,
    - 코드 복잡성이 올라가기에 이 부분은 뷰에서만 a1 이란 좌표를 알아도 충분하다고 판단했다.
    - 코드 복잡성과 추상화 사이의 트레이드오프 관계라 판단했다.

  <img src="./image/position.png" height="300" width="300">

  ```
    (1,8), (2,8), (3,8), (4,8), (5,8), (6,8), (7,8), (8,8)
    (1,7), (2,7), (3,7), (4,7), (5,7), (6,7), (7,7), (8,7)
    (1,6), (2,6), (3,6), (4,6), (5,6), (6,6), (7,6), (8,6)
    (1,5), (2,5), (3,5), (4,5), (5,5), (6,5), (7,5), (8,5)
    (1,4), (2,4), (3,4), (4,4), (5,4), (6,4), (7,4), (8,4)
    (1,3), (2,3), (3,3), (4,3), (5,3), (6,3), (7,3), (8,3)
    (1,2), (2,2), (3,2), (4,2), (5,2), (6,2), (7,2), (8,2)
    (1,1), (2,1), (3,1), (4,1), (5,1), (6,1), (7,1), (8,1) -> 모델이 아는 좌표
  
    (h,8), (h,7), (h,6), (h,5), (h,4), (h,3), (h,2), (h,1)
    (g,8), (g,7), (g,6), (g,5), (g,4), (g,3), (g,2), (g,1)
    (f,8), (f,7), (f,6), (f,5), (f,4), (f,3), (f,2), (f,1)
    (e,8), (e,7), (e,6), (e,5), (e,4), (e,3), (e,2), (e,1)
    (d,8), (d,7), (d,6), (d,5), (d,4), (d,3), (d,2), (d,1)
    (c,8), (c,7), (c,6), (c,5), (c,4), (c,3), (c,2), (c,1)
    (b,8), (b,7), (b,6), (b,5), (b,4), (b,3), (b,2), (b,1)
    (a,8), (a,7), (a,6), (a,5), (a,4), (a,3), (a,2), (a,1) -> 뷰가 아는 좌표. a1 이란 의미를 뷰만 알자~
  ```

- 역할 분담에 대한 고민
    - Game: 게임의 각 단계 관리하기 (start -> move -> end)
    - Board: 현재 기물들의 위치 관리 -> `기물의 위치`
    - Piece: 기물들의 행마법 검증 -> `기물`
    - Movement: 두 위치 간 관계 관리 -> `위치`
        - Position: 위치 값
