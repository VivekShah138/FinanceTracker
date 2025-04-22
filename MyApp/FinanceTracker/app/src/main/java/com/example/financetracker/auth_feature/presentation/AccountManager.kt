package com.example.financetracker.auth_feature.presentation

import android.app.Activity
import android.util.Log
import androidx.credentials.CreatePasswordRequest
import androidx.credentials.CredentialManager
import androidx.credentials.GetCredentialRequest
import androidx.credentials.GetPasswordOption
import androidx.credentials.PasswordCredential
import androidx.credentials.exceptions.CreateCredentialCancellationException
import androidx.credentials.exceptions.CreateCredentialException
import androidx.credentials.exceptions.GetCredentialCancellationException
import androidx.credentials.exceptions.GetCredentialException
import com.example.financetracker.R
import com.example.financetracker.auth_feature.presentation.login.GoogleSignInResult
import com.example.financetracker.auth_feature.presentation.login.LogInResult
import com.example.financetracker.auth_feature.presentation.forgot_password.ResetPasswordWithCredentialResult
import com.example.financetracker.auth_feature.presentation.forgot_password.ResetPasswordWithEmailResult
import com.example.financetracker.auth_feature.presentation.register.RegisterResult
import com.google.android.libraries.identity.googleid.GetGoogleIdOption
import com.google.android.libraries.identity.googleid.GoogleIdTokenCredential
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.FirebaseAuthException
import com.google.firebase.auth.FirebaseAuthUserCollisionException
import com.google.firebase.auth.GoogleAuthProvider
import kotlinx.coroutines.tasks.await
import java.util.concurrent.CancellationException

class AccountManager(
    private val activity: Activity
) {
    private val firebaseAuth = FirebaseAuth.getInstance()
    private val credentialManager = CredentialManager.create(activity)




    suspend fun registerUser(username: String, password: String) : RegisterResult {
        return try{
            firebaseAuth.createUserWithEmailAndPassword(username, password).await()

            try{
                credentialManager.createCredential(
                    context = activity,
                    request = CreatePasswordRequest(
                        id = username,
                        password = password
                    )
                )
                RegisterResult.Success
            }
            catch (e: CreateCredentialCancellationException){
                e.printStackTrace()
                RegisterResult.CredentialCancelled
            }catch (e: CreateCredentialException){
                e.printStackTrace()
                RegisterResult.CredentialFailure
            }
        }catch (e: FirebaseAuthUserCollisionException) {
            e.printStackTrace()
            RegisterResult.FirebaseAuthUserCollisionException
        }catch (e: FirebaseAuthException){
            e.printStackTrace()
            RegisterResult.RegistrationFailure
        }catch (e: Exception){
            e.printStackTrace()
            RegisterResult.UnknownFailure
        }
    }

    suspend fun loginInUser(username: String, password: String): LogInResult {
        return try {
            val credentialResponse = try {
                credentialManager.getCredential(
                    context = activity,
                    request = GetCredentialRequest(
                        credentialOptions = listOf(GetPasswordOption())
                    )
                )
            } catch (e: Exception) {
                // Handle when the user cancels the credentials dialog
                null
            }

            val credential = credentialResponse?.credential as? PasswordCredential

            val authResult = if (credential != null) {
                // Use saved credentials to sign in
                firebaseAuth.signInWithEmailAndPassword(credential.id, credential.password).await()
            } else {
                // Fallback to manual login with provided username and password
                firebaseAuth.signInWithEmailAndPassword(username, password).await()
            }

            LogInResult.Success(authResult.user?.email ?: "Unknown")
        } catch (e: FirebaseAuthException) {
            e.printStackTrace()
            LogInResult.Failure // Firebase-specific failure
        } catch (e: Exception) {
            e.printStackTrace()
            LogInResult.Failure // General failure (network issues, etc.)
        }
    }


    suspend fun resetPasswordWithCredential() : ResetPasswordWithCredentialResult {
        return try {
            val credentialResponse = credentialManager.getCredential(
                context = activity,
                request = GetCredentialRequest(
                    credentialOptions = listOf(GetPasswordOption())
                )
            )

            val credential = credentialResponse.credential as? PasswordCredential
            val authResult = firebaseAuth.signInWithEmailAndPassword(credential!!.id, credential.password).await()
            return ResetPasswordWithCredentialResult.CredentialLoginSuccess(authResult.user?.email ?: "Unknown")
        }catch (e:CreateCredentialCancellationException){
            e.printStackTrace()
            ResetPasswordWithCredentialResult.Cancelled
        } catch (e:CreateCredentialException){
            e.printStackTrace()
            ResetPasswordWithCredentialResult.CredentialLoginFailure
        } catch (e:Exception){
            e.printStackTrace()
            ResetPasswordWithCredentialResult.UnknownFailure
        }
    }


    suspend fun resetPasswordWithEmail(email: String) : ResetPasswordWithEmailResult {
        return try {
            firebaseAuth.sendPasswordResetEmail(email).await()
            ResetPasswordWithEmailResult.EmailSuccess
        } catch (e:FirebaseAuthException){
            e.printStackTrace()
            ResetPasswordWithEmailResult.AuthFailure
        } catch (e:CancellationException){
            e.printStackTrace()
            ResetPasswordWithEmailResult.Cancelled
        } catch (e:Exception){
            e.printStackTrace()
            ResetPasswordWithEmailResult.UnknownFailure
        }
    }



    suspend fun signInWithGoogle() : GoogleSignInResult {
        return try{
            val response = credentialManager.getCredential(
                context = activity,
                request = getSignInRequest()
            )

            val googleCredential = GoogleIdTokenCredential.createFrom(response.credential.data)

            val firebaseCredential = GoogleAuthProvider.getCredential(googleCredential.idToken,null)

            val authResult = firebaseAuth.signInWithCredential(firebaseCredential).await()
            Log.d("AccountManager","sucesss")
            GoogleSignInResult.Success(authResult.user?.email ?: "Unknown")
        }catch (e: GetCredentialCancellationException){
            e.printStackTrace()
            Log.d("AccountManager","error ${e.localizedMessage}")
            GoogleSignInResult.Cancelled
        }catch (e: GetCredentialException){
            e.printStackTrace()
            Log.d("AccountManager","error ${e.localizedMessage}")
            GoogleSignInResult.Failure
        }catch (e: Exception){
            e.printStackTrace()
            Log.d("AccountManager","error ${e.localizedMessage}")
            GoogleSignInResult.Failure
        }
    }

    private fun getSignInRequest() : GetCredentialRequest{
        val googleIdOption: GetGoogleIdOption = GetGoogleIdOption.Builder()
            .setFilterByAuthorizedAccounts(false)
            .setServerClientId(activity.getString(R.string.web_client_id))
            .build()

        // It packages the Google Sign-In option inside a request
        val request = GetCredentialRequest.Builder()
            .addCredentialOption(googleIdOption)
            .build()

        return request
    }
}