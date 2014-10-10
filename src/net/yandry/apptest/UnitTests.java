package net.yandry.apptest;

import static org.junit.Assert.*;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import net.yandry.tcpclient.Worker;
import net.yandry.utils.Util;

import org.junit.Before;
import org.junit.Test;

public class UnitTests {

	private Worker workerClient;

	@Before
	public void setUp() {
		workerClient = new Worker(1111, 1);
	}

	@Test
	public void getBytesWithIntSeven() {
		assertArrayEquals(Util.getBytes(7), new byte[] { 0, 0, 0, 7 });
	}

	@Test
	public void getBytesWithShortSeven() {
		assertArrayEquals(Util.getBytes((short) 7), new byte[] { 0, 7 });
	}

	@Test
	public void getMessageWorkerClientWithIdOne()
			throws IllegalAccessException, IllegalArgumentException,
			InvocationTargetException {
		final Method[] methods = workerClient.getClass().getDeclaredMethods();
		for (int i = 0; i < methods.length; ++i) {
			if (methods[i].getName().equals("getMessage")) {
				methods[i].setAccessible(true);
				Object obj_res = methods[i].invoke(workerClient);

				byte[] result = (byte[]) obj_res;
				byte[] expected = new byte[] { 0, 0, 0, 10, 0, 1, 84, 104, 114,
						101, 97, 100, 45, 49 };

				assertArrayEquals(result, expected);

				break;
			}
		}
	}
}
