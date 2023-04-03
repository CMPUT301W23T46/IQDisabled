package com.example.qr;

import static org.junit.Assert.*;

import android.database.sqlite.SQLiteDatabase;

import com.google.android.gms.tasks.Task;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import static org.mockito.Mockito.when;
import static org.mockito.Mockito.*;

import java.security.NoSuchAlgorithmException;
import java.util.concurrent.ExecutionException;

public class DataBaseHelperTest {
    private FirebaseFirestore mockDb;
    private CollectionReference mockCollectionRef;
    private DocumentReference mockDocumentRef;
    private Task<DocumentSnapshot> mockTask;
    private OnCheckQRCodeExistListener mockListener;

    private DataBaseHelper databaseManager;

    @Before
    public void setUp() {
        mockDb = mock(FirebaseFirestore.class);
        mockCollectionRef = mock(CollectionReference.class);
        mockDocumentRef = mock(DocumentReference.class);
        mockTask = mock(Task.class);
        mockListener = mock(OnCheckQRCodeExistListener.class);

        when(mockDb.collection(eq("Players"))).thenReturn(mockCollectionRef);
        when(mockCollectionRef.document(anyString())).thenReturn(mockDocumentRef);
        when(mockDocumentRef.get()).thenReturn(mockTask);

        databaseManager = new DataBaseHelper();
        databaseManager.db = mockDb;
    }

    @Test
    public void testCheckUserNameExist_UserExists() throws Exception {
        String testUsername = "test_username";
        DocumentSnapshot mockDocumentSnapshot = mock(DocumentSnapshot.class);

        when(mockDocumentSnapshot.exists()).thenReturn(true);
        when(mockTask.isComplete()).thenReturn(true);
        when(mockTask.getResult()).thenReturn(mockDocumentSnapshot);

        databaseManager.checkUserNameExist(testUsername, mockListener);

        verify(mockDocumentRef).get();
        verify(mockListener).onSuccess(true);
    }

    @Test
    public void testCheckUserNameExist_UserDoesNotExist() throws Exception {
        String testUsername = "test_username";
        DocumentSnapshot mockDocumentSnapshot = mock(DocumentSnapshot.class);

        when(mockDocumentSnapshot.exists()).thenReturn(false);
        when(mockTask.isComplete()).thenReturn(true);
        when(mockTask.getResult()).thenReturn(mockDocumentSnapshot);

        databaseManager.checkUserNameExist(testUsername, mockListener);

        verify(mockDocumentRef).get();
        verify(mockListener).onSuccess(false);
    }

    @Test
    public void testCheckUserNameExist_TaskFailed() throws Exception {
        String testUsername = "test_username";
        Exception mockException = mock(Exception.class);

        when(mockTask.isComplete()).thenReturn(false);
        when(mockTask.getException()).thenReturn(mockException);

        databaseManager.checkUserNameExist(testUsername, mockListener);

        verify(mockDocumentRef).get();
        verify(mockListener, never()).onSuccess(anyBoolean());
    }

    @After
    public void tearDown() {
        databaseManager.db = null;
    }
}
