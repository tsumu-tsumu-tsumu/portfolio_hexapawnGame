
//
//package game.main;
//
//public class CpuTurn {
//	public static int[] CpuTurn(int[][] field,int[][]cpuSave, int round) {
//		//表示
//		String [][] cpuField = {{"a3","b3","c3"},{"a2","b2","c2"},{"a1","b1","c1"}};
////		int[] pawn1 = {2, 0};
////		int[] pawn2 = {2, 1};
////		int[] pawn3 = {2, 2};
////		int[][] pawnList = {{2, 0}, {2, 1}, {2, 2}};
////		int count = 1;
//		
//		int[] save = new int[2]; //記録用、新たに出現したポーンのマスを記録
//		
////		//ルール上可能な手をランダムで選択(CPUはにゅうりょくしないから後で修正)
////		int cpuSelectP = 0;//移動先の行の座標
////		int cpuSelectF = 0;//移動先の列の座標
//		
//	//ポーン移動
//		//斜め前にプレイヤーのポーンがある場合
//		for(int i = 2; i >= 0; i--) { //盤上の左斜め上から1マスずつまわす
//			
//			for(int j = 2; j >= 0; j--) { 
//				//参照マスのなかにプレイヤーポーンがあり、←斜め上にCPUのポーンがある時
//				if(field[i][j] == 1 && (field[i + 1][j - 1] == 2) && j != 0) { 
//					field[i + 1][j - 1] = 0; //CPUのポーンが消える
//					field[i][j] = 2; //CPUのポーンが相手のポーンのマスに上書きされる
//					save [0] = i; 
//					save [1] = j;
//					return save;
//					
//				//参照マスのなかにプレイヤーポーンがあり、→斜め上にCPUのポーンがある時
//				}else if(field[i][j] == 1 && (field[i + 1][j + 1] == 2 && j != 2)) {
//					field[i + 1][j + 1] = 0; //CPUのポーンが消える
//					field[i][j] = 2; //CPUのポーンが相手のポーンのマスに上書きされる
//					save [0] = i; 
//					save [1] = j;
//					return save;
//			}
//		}
//		
//		//斜めに接していないとき、ひとつ前の空きマスに直進する。（左優先）
//		for(int i2 = 2; i2 >= 0; i2--) { 
//			
//			for(int j2 = 2; j2 >= 0; j2--) { 
//				//
//				if(field[i2][j2] == 0 && field[i2 - 1][j2] == 2 && i != 2) {
//					field[i2 - 3][j2 - 3] = 0;//CPUのポーンが消える
//					field[i2][j2] = 2; //CPUのポーンがひとつ前の空いたマスに直進する。
//					save [0] = i2; 
//					save [1] = j2;
//					return save;
//				}
//					
//			}
//			
//
//			}
//		}
//	
//		System.out.println();
//		System.out.println("マッチ箱： " + cpuField[cpuSave[(round - 2)][1]][cpuSave[(round - 2)][0]] + "を" + cpuField[save[0]][save[1]] + "へ");
//		System.out.println();
//		return save;
//	}
//}					
	
					
					
					
//					pawnList[i][j] -= 1; // 右斜め前に座標を変える(行も列も-1ずらすのでまとめている)
//				
//				
//				}else if(field[i - 1][j + 1] == 1){ //左斜め
//					
//					if(j == 0) { //ポーンの行の座標をずらす
//						pawnList[i][j] -= 1;
//						
//					}else { //ポーンの列の座標をずらす
//						pawnList[i][j] += 1;
//					}
//					
//				}else{//斜め前に敵がいないとき
//					
//					//敵の斜め前から逃げる処理
//				}

			
//			for(int j = 0; j < pawn[0].length ; j++)  {
//				//斜め前に敵がいるとき
//				if(field[(pawn1[0] )][cpuSelectP - 1] == 1 || field[cpuSelectF - 1][cpuSelectP + 1] == 1) {
//					field[cpuSelectF][cpuSelectP] = 2;
//				
//				//斜め前に敵がいないとき
//				}else {
//					
//					//敵の斜め前から逃げる処理
//					
//				}
//		
//			}
//		}
	
//		//ポーンの出現
//		switch(field[cpuSelectF][cpuSelectP]) {
//			case 0:
//				if(cpuSelectF == cpuSave[(round - 2)]) {
//					
//				}else {
//					CpuTurn(field, selectButtun, cpuSave, round);
//				}
//				break;
//			case 1:
//				
//				break;
//			case 2:
//				
//				break;
//		}
		
		
		
package hexapawnNotRefactor;

import java.util.Random;

public class CpuTurn {
	public static int[] CpuTurn(int[][] field, int[][]selectButtun, int round, int nullCount) {
		
		//CPUの選択:全件ループを利用して選択肢を網羅。最大81パターンで必ず終わる。
		int[] cpuSelectP = {1, 2, 3, 4, 5, 6, 7, 8, 9};		//移動前のマスの選択
//		int[] cpuSelectF = {1, 2, 3, 4, 5, 6, 7, 8, 9};		//移動後のマスの選択
//		int cpuSelectP = new java.util.Random().nextInt(9) + 1;
//		int cpuSelectF = new java.util.Random().nextInt(9) + 1;
		
		//配列をランダムにシャッフルさせる。中身をシャッフルさせることで実質ランダムに出力
		ShuffleArray(cpuSelectP);
//		ShuffleArray(cpuSelectF);
		
		//添え字持ち出し用
		int pi = 100;
		int pj = 100;//CPUの選択した移動前ポーンの行位置をループ外に持ち出す
		int fi = 100;
		int fj = 100;//jをループ外に持ち出す。
		
		//ループ途中離脱用
		boolean isCompleted = false;  //ランダムに選択した手が選択可能だった時:true

		
		//↑で選択したポーンを削除
		for(int i = 0; i < field.length; i++) {
			
			
			for(int j = 0; j < field[0].length; j++) {	

				if(selectButtun[i][j] == cpuSelectP[i] && field[i][j] == 2) {
					pi = i;//配列cpuSelectPの現時点での選択している値の添え字
					pj = j;//移動前のマスの行を確定。
					field[pi][pj] = 0; 
//					System.out.println("移動前のマスの行は" + pj + "移動前のマスの列は" + pi + "選んだボタンは" +cpuSelectP[pi]);//テスト用
					break;
					//　移動前を消す分岐のelse
				}/*else {
					System.out.println("●");
//					return null;
					//CpuTurn(field, selectButtun, cpuSave, round);
				}*/
				
			}
			
			if(pj != 100) {
				break;
			}
		}
		
		if(pj == 100) {	//質問　理論上ここは通らないはずなのになぜここを通過するのか？
//			System.out.println("最初のコマが決まらなかったのでやりなおし");//テスト用
			return null;
		}
		
		//ポーンの出現:パターン1
		//該当フィールドが移動前マスの斜め前に来た時、そこに敵のポーンがあればポーンを出現させる
		
		for(int i = 0; i < field.length; i++) {
			
			for(int j = 0; j < field[0].length; j++) {	

				switch(cpuSelectP[pi]) {
					case 7:
					case 4:
						if(/*cpuSelectF[j]*/selectButtun[i][j] == (cpuSelectP[pi] - 2) && field[i][j] == 1) {
							field[i][j] = 2;
							fi = i;
							fj = j;
//							System.out.println("--ここからパターン1.1--　セレクトPの選択：" + cpuSelectP[pi] + "ボタン位置:" + selectButtun[i][j]);//テスト用
							isCompleted = true;
						}/*else {
						System.out.println("●");
//						return null;
							//CpuTurn(field, selectButtun, cpuSave, round);
					}*/
						break;
					case 8:
					case 5:
						if(field[i][j] == 1 && (/*cpuSelectF[j]*/selectButtun[i][j] == (cpuSelectP[pi] - 2)) ||(field[i][j] == 1 && /*cpuSelectF[j]*/selectButtun[i][j] == (cpuSelectP[pi] - 4))) {									
							field[i][j] = 2;
							fi = i;
							fj = j;	
//							System.out.println("--ここからパターン1.2--　セレクトPの選択：" + cpuSelectP[pi] + "ボタン位置" + selectButtun[i][j]);//テスト用
							isCompleted = true;
						}/*else {
//						return null;
						//CpuTurn(field, selectButtun, cpuSave, round);
						}*/
						break;
					case 9:
					case 6:
						if(field[i][j] == 1 && /*cpuSelectF[j]*/selectButtun[i][j] == (cpuSelectP[pi] - 4)) {
							field[i][j] = 2;
							fi = i;
							fj = j;	
//							System.out.println("--ここからパターン1.3--　セレクトPの選択：" + cpuSelectP[pi] + "ボタン位置" + selectButtun[i][j]);//テスト用
							isCompleted = true;								
							}
							break;
//					default:
//						return null;
//						//CpuTurn(field, selectButtun, cpuSave, round);		
				}//スイッチ
				
				//この時点でtrueになったら戻り値を返す
				if(isCompleted == true) {
					//表示
					String [][] cpuField = {{"a1","b1","c1"},{"a2","b2","c2"},{"a3","b3","c3"}};
//					
//					switch(round) {
//					case 1:
						System.out.println();
						System.out.println("CPU： " + cpuField[2][pj] + "を" + cpuField[fi][fj] + "へ");
						System.out.println();
//						break;
//						default:
//						System.out.println();
//						System.out.println("CPU： " + cpuField[pi][pj] + "を" + cpuField[fi][fj] + "へ");
//						System.out.println();
//						break;
//					}

							
					//記録用戻り値
					int[] save = {selectButtun[fi][fj], selectButtun[pi][pj]};
//					System.out.println(selectButtun[fi][fj]);//テスト用
//					System.out.println(selectButtun[pi][pj]);//テスト用
					return save;
				}

			}
			

			
		
		//もし、斜め前に取れる敵のポーンがいなかったとき、該当マスがひとつ前に進むマスにあるときポーンを出現
			for(int k = 0; k < field.length; k++) {
				
				if(selectButtun[i][k] == /*cpuSelectF[j] && cpuSelectF[j] ==*/ (cpuSelectP[pi] - 3) && field[i][k] == 0) {
					field[i][k] = 2;
					fi = i;
					fj = k;	
//					System.out.println("--ここからパターン2--　セレクトPの選択：" + cpuSelectP[pi] + "ボタン位置" + selectButtun[i][k]);//テスト用
					isCompleted = true;
					break;
					
				}//else if(selectButtun[i][j] == cpuSelectF[j]){ //選べない手を選んだ時の処理
//					break;
//					return null;
//					//CpuTurn(field, selectButtun, cpuSave, round);
//				}
				
			}//内側のfor2
			
			
			
		//trueになったら戻り値を返す。falseのままならnullを返してmainメソッド経由でループ
			if(isCompleted == true) {
//			if(isCompleted == true && f != 100) {
				
				//表示
				String [][] cpuField = {{"a1","b1","c1"},{"a2","b2","c2"},{"a3","b3","c3"}};
				
//				switch(round) {
//				case 1:
					System.out.println();
					System.out.println("CPU： " + cpuField[2][pj] + "を" + cpuField[fi][fj] + "へ");
					System.out.println();
//					break;
//					default:
//					System.out.println();
//					System.out.println("CPU： " + cpuField[pi][pj] + "を" + cpuField[fi][fj] + "へ");
//					System.out.println();
//					break;
//				}

						
				//記録用戻り値
				int[] save = {selectButtun[fi][fj], selectButtun[pi][pj]};
//				System.out.println(selectButtun[fi][fj]);//テスト用
//				System.out.println(selectButtun[pi][pj]);//テスト用
				return save;
				
			}
			
		}//外側のfor2
		
		//全て回して決まらない場合はnullを返しまた最初から繰り返す。
		
		field[pi][pj] = 2;	//選択して削除したポーンを復活させる。
//		System.out.println("false");//テスト用
		return null;
		}



	
	//配列をシャッフルさせるメソッド
	public static void ShuffleArray(int[] cpuSelect)/*main(String[] args)*/ {
		
		Random random = new Random();	//標準装備のクラスからランダムインスタンスを作成
//		int[] cpuSelect = {1, 2, 3, 4, 5, 6, 7, 8, 9};//確認用
		
		int index, temp;
		
		for (int i = cpuSelect.length - 1; i >= 0; i--)//0の変数を変えた。なぜネットでは入れなかったのか？https://www.choge-blog.com/programming/javaarrayrandomsort/
		{
		    index = random.nextInt(i + 1);	//メソッドを呼び出し  　10未満1以上の乱数を代入。
		    
		    //前後を入れ替え
		    temp = cpuSelect[index];
		    cpuSelect[index] = cpuSelect[i];
		    cpuSelect[i] = temp;
		    
//		    System.out.println(cpuSelect[i]);　//確認用
		}

	}

}
