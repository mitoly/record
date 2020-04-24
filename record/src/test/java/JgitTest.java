import org.eclipse.jgit.api.CloneCommand;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.Status;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.internal.storage.file.FileRepository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Set;

public class JgitTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(JgitTest.class);
    private static String branch = "master";

    public static void main(String[] args) {
        //设置远程服务器上的用户名和密码
        UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider =new
                UsernamePasswordCredentialsProvider("mitoly","z472395913");

        //克隆代码库命令
        CloneCommand cloneCommand = Git.cloneRepository();

        Git git= null;
        try {
            git = cloneCommand.setURI("https://gitee.com/mitoly/testJgit.git") //设置远程URI
                    .setBranch("master") //设置clone下来的分支
                    .setDirectory(new File("D:\\tesst\\")) //设置下载存放路径
                    .setCredentialsProvider(usernamePasswordCredentialsProvider) //设置权限验证
                    .call();
        } catch (Exception e) {
            e.printStackTrace();
        }

        System.out.print(git.tag());
    }


    //克隆仓库
    public static String cloneRepository(String url, String localPath, UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider) {
        try {
            LOGGER.info("开始下载......");

            File gitFilePath = new File(localPath);

            Git git = Git.cloneRepository()
                    .setURI(url)
                    .setDirectory(gitFilePath)
                    .setBranch(branch)
                    .setCredentialsProvider(usernamePasswordCredentialsProvider)
//                    .setCloneAllBranches(true)
                    .call();

            LOGGER.info("下载完成......");

            return "success";
        } catch (Exception e) {
            return "error";
        }
    }

    /**
     * 本地仓库新增文件 往本地仓库增加文件
     *
     * @param localPath 本地git地址
     * @throws IOException
     * @throws GitAPIException
     */
    public static void addAllFileToRepository(String localPath) throws IOException, GitAPIException {
        // git仓库地址
        Git git = new Git(new FileRepository(localPath + ".git"));
        // 添加文件
        git.add().addFilepattern(".").call();
        LOGGER.info("AddAll文件完成");
    }

    /**
     * 本地仓库新增文件 往本地仓库增加文件
     *
     * @param localPath 本地git地址
     * @param addFilePath 新增的文件地址
     * @throws IOException
     * @throws GitAPIException
     */
    public static void addFileToRepository(String localPath, String addFilePath) throws IOException, GitAPIException {
        // git仓库地址
        Git git = new Git(new FileRepository(localPath + ".git"));
        // 添加文件
        git.add().addFilepattern(addFilePath).call();
        LOGGER.info("Add文件完成");
    }

    /**
     *
     * @param localPath 本地git地址
     * @param rmFilePath 删除的文件地址
     * @throws IOException
     * @throws GitAPIException
     */
    public static void rmFileToRepository(String localPath, String rmFilePath) throws IOException, GitAPIException {
        // git仓库地址
        Git git = new Git(new FileRepository(localPath + ".git"));
        // 删除文件
        git.rm().addFilepattern(rmFilePath).call();
        LOGGER.info("删除文件完成");
    }

    /**
     * 本地提交代码到远程仓库
     *
     * @param localPath 本地git地址
     * @param commitMessage    提交的注释信息
     * @throws IOException
     * @throws GitAPIException
     * @throws JGitInternalException
     */
    public static void commitToRepository(String localPath, String commitMessage) throws IOException, GitAPIException, JGitInternalException {
        // git仓库地址
        Git git = new Git(new FileRepository(localPath + ".git"));
        // 提交代码
        git.commit().setMessage(commitMessage).call();
        LOGGER.info("Commit完成");
    }

    /**
     * 拉取远程仓库内容到本地
     *
     * @param localPath 本地git地址
     * @throws IOException
     * @throws GitAPIException
     */
    public static void pull(String localPath, UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider) throws IOException, GitAPIException {
        // git仓库地址
        Git git = new Git(new FileRepository(localPath + ".git"));
        git.pull().setRemoteBranchName(branch).setCredentialsProvider(usernamePasswordCredentialsProvider)
                .call();
        LOGGER.info("提交前拉取完成");
    }

    /**
     * push本地代码到远程仓库地址
     *
     * @param localPath 本地git地址
     * @throws IOException
     * @throws JGitInternalException
     * @throws GitAPIException
     */
    public static void push(String localPath, UsernamePasswordCredentialsProvider usernamePasswordCredentialsProvider) throws IOException, JGitInternalException, GitAPIException {
        // git仓库地址
        Git git = new Git(new FileRepository(localPath + ".git"));
        git.push().setPushAll().setCredentialsProvider(usernamePasswordCredentialsProvider).call();
        LOGGER.info("推送完成");
    }

    /**
     * 还原本地
     * @param localPath
     * @throws IOException
     * @throws JGitInternalException
     * @throws GitAPIException
     */
    public static void reset(String localPath) throws IOException, JGitInternalException, GitAPIException {
        Git git = new Git(new FileRepository(localPath + ".git"));
        git.reset().call();
        LOGGER.info("还原完成");
    }

    /**
     * 检查本地仓库是否变更
     * @throws IOException
     * @throws GitAPIException
     */
    public static boolean checkStatus(String localPath) throws IOException, GitAPIException {
        Git git = new Git(new FileRepository(localPath + ".git"));
        Status status = git.status().call();
        LOGGER.info("Git checkStatus: " + status.hasUncommittedChanges());
        return status.hasUncommittedChanges();
    }

    public static Set<String> getUntrackedStatus(String localPath) throws IOException, GitAPIException {
        Git git = new Git(new FileRepository(localPath + ".git"));
        Status status = git.status().call();
        LOGGER.info("Git Untracked: " + status.getUntracked());
        return status.getUntracked();
    }

    public static Set<String> getUnCommittedChangesStatus(String localPath) throws IOException, GitAPIException {
        Git git = new Git(new FileRepository(localPath + ".git"));
        Status status = git.status().call();
        LOGGER.info("Git UncommittedChanges: " + status.getUncommittedChanges());
        return status.getUncommittedChanges();
    }

    public static Set<String> getChangesStatus(String localPath) throws IOException, GitAPIException {
        Git git = new Git(new FileRepository(localPath + ".git"));
        Status status = git.status().call();
        LOGGER.info("Git Change: " + status.getChanged());
        return status.getChanged();
    }

    public static Set<String> getModifiedStatus(String localPath) throws IOException, GitAPIException {
        Git git = new Git(new FileRepository(localPath + ".git"));
        Status status = git.status().call();
        LOGGER.info("Git Modified: " + status.getModified());
        return status.getModified();
    }

    public static Iterator<RevCommit> getLog(String localPath) throws IOException, GitAPIException {
        Git git = new Git(new FileRepository(localPath + ".git"));
        Iterator<RevCommit> it = git.log().call().iterator();
        return it;
    }
}
