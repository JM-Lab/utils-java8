package kr.jm.utils.helper;

import static org.junit.Assert.assertEquals;

import java.util.List;

import org.junit.Before;
import org.junit.Test;

import kr.jm.utils.datastructure.JMCollections;

public class JMStringTest {

	@Before
	public void setUp() throws Exception {
	}

	@Test
	public void test() {
		List<String> buildList = JMCollections.buildList("jm", "jm");
		System.out.println(JMString.joiningWithComma(buildList));
	}

	@Test
	public void testTruncate() throws Exception {
		String testString = "sldkjflkasjdlkfjalksdjf;ajsdl<딸 부잣집 막내딸, OOO>초등학생 때 아버지를 여읜 저는 막내에 매진하고 계신 따뜻하고 정 많으신 어머니와 무역회사에 근무 중인 큰 언니, 국립농산물품질관리원 인턴인 작은 언니 모두 지금 OOOOOO의 문을 두드리는 밝고 당찬 막내의 든든한 후원자가 되어주고 있습니다. (290자)딸 부잣집 막내딸이 가슴에 품은 白玉堂中笑滿長>아버지께서는 제가 초등학생일 때 불의의 교통사고로 가족의 곁을 떠나가셨습니다. 늘 가족의 든든한 버팀목이 되어주셨던 아버지의 부재는 온 가족을 고통 속으로 밀어 넣는 것이었습니다. 그러나 어머니께서는 아버지 대신 가족의 생계를 책임져야 하는 힘든 상황 속에서도 삶에 대한 희망을 포기하지 않으셨습니다. 아버지께서 남겨주신 세상 전부를 준다 해도 바꿀 수 없는 소중한 선물인 세 딸을 올곧은 사람으로 성장시키기 위해서였습니다. 세 자매의 막내 딸이었던 저는 어머니 대신 살림을 꾸리느라 바쁜 언니들 대신 일터에서 돌아오시는 어머니를 마중하는 일을 도맡곤 했습니다. 당시 젊은 나이에 홀로 되어 세 딸들을 키우시느라 고생하시는 어머니께 저희 세 자매가 드릴 수 있는 것은 환한 웃음뿐이었습니다. 세 딸을 위해 여자로서의 삶을 포기하며 ‘엄마’라는 이름으로만 남아주셨던 어머니께서는 평생의 일터였던 김해공항에서 퇴직하신 후 절에 다니시며 어려운 이웃들을 위한 봉사활동에 전념하고 계십니다. 누구보다 강한 모습으로 저희 세 자매에게 헌신적인 사랑을 베풀어주신 어머니께서는 제 인생의 롤모델 이시기도 합니다. 현재 무역회사에서 경리 업무를 담당하고 있는 큰 언니와 농산물 품질관리원에서 인턴으로 근무하며 자신의 미래를 개척해나가고 있는 작은 언니, 그리고 눈물 대신 웃음으로 삶을 대처하는 긍정의 마인드를 가진 막내 딸에게 어머니께서 물려주신 가훈은 ‘깨끗한 집안에는 항상 웃음꽃이 핀다’라는 뜻을 가진 ‘白玉堂中笑滿長’입니다. 저희 집 거실 중앙에 표구된 이 가훈은 오랫동안 서예를 해오신 어머니께서 직접 쓰신 글귀이기도 합니다. 어머니의 가르침 속에서 자신의 위치에서 최선을 다하며 스스로에게 부끄럽지 않은 사람이 되겠다는 제 자신과의 약속을 akjshdfhaklsdhfazzzㅋㅋㅋ 미나어리ㅏㅋㅋ";
		System.out.println(testString.length());
		System.out.println(testString.getBytes().length);
		String trunctedString = JMString.truncate(testString, 2000, "...");
		System.out.println(trunctedString.length());
		System.out.println(trunctedString.getBytes().length);
		System.out.println(trunctedString);
		assertEquals(2000, trunctedString.getBytes().length);
		trunctedString = JMString.truncate(testString, 2000);
		System.out.println(trunctedString.length());
		System.out.println(trunctedString.getBytes().length);
		System.out.println(trunctedString);
		assertEquals(2000, trunctedString.getBytes().length);
		trunctedString = JMString.truncate(testString, 100, "...");
		System.out.println(trunctedString.length());
		System.out.println(trunctedString.getBytes().length);
		System.out.println(trunctedString);
		assertEquals(99, trunctedString.getBytes().length);
	}

}
